import { useState } from "react";
import OSForm from "./OSForm";
import OrcamentoForm from "./OrcamentoForm";
import { Reserva, produtosReservados } from "../../Service/Entities/Reserva";
import { NovoProduto } from "../../Service/Entities/Produto";
import { Midia, OSCreateRequest } from "../../Service/Entities/OS";
import { createOS, uploadMidia } from "../../Service/api/OSapi";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { storage } from "../../Service/Firebase/Firebase";
import { getDownloadURL, ref, uploadBytes } from "firebase/storage";

export default function Form() {
  const navigate = useNavigate();

  const [reserva, setReserva] = useState<Reserva>();
  const [osRequest, setOsRequest] = useState<OSCreateRequest>();
  const [imagens, setImagens] = useState<Blob[]>([]);
  const [midiaDesc, setMidiaDesc] = useState<string>("");

  const setarReserva = (
    produtosExistentesInput: produtosReservados[],
    produtosNovosInput: NovoProduto[],
    maoDeObraInput: number
  ) => {
    const Reserva = {
      produtosExistentes: produtosExistentesInput,
      produtosNovos: produtosNovosInput,
      maoDeObra: maoDeObraInput,
    };
    setReserva(Reserva);
  };

  const setarOS = (OSRequest: OSCreateRequest) => {
    setOsRequest(OSRequest);
  };

  const adicionarImagens = (novasImagens: Blob[]) => {
    setImagens((prevData) => [...prevData, ...novasImagens]);
  };

  const envia = async () => {
    const request = {
      nome: osRequest?.nome,
      telefone: osRequest?.telefone,
      endereco: osRequest?.endereco,
      cpf: osRequest?.cpf,
      equipamento: osRequest?.equipamento,
      numeroSerie: osRequest?.numeroSerie,
      servico: osRequest?.servico,
      dataSaida: osRequest?.dataSaida,
      funcionarioId: osRequest?.funcionarioId,
      observacao: osRequest?.observacao,
      comentarios: osRequest?.comentarios,
      reserva: reserva,
    };



    const inputsObrigatorios = [
      { propriedade: "Nome", valor: request.nome },
      { propriedade: "Equipamento", valor: request.equipamento },
      { propriedade: "Serviço", valor: request.servico },
    ];

    if (inputsObrigatorios.some((e) => e.valor?.trim() == "")) {
      console.error("Campo inválido");
      toast.error(
        `O Campo ${inputsObrigatorios.find((e) => e.valor?.trim() == "")?.propriedade} está vazio`
      );
      return;
    }

    try {
      const OSResponse = await createOS(request);

      const midia: Midia = {
        descricao: midiaDesc,
        links: [],
      };

      // Faz o upload das imagens relacionadas à OS
      if (!(imagens.length === 0)) {
        toast.info("Armazenando mídias")
        for (const imagem of imagens) {
          const storageRef = ref(
            storage, `Imagens/${OSResponse.id}/${OSResponse.id}_${gerarNumeroAleatorio()}`
          );
          const uploadTask = uploadBytes(storageRef, imagem);

          // Pega o link da imagem após o upload
          await uploadTask.then(async (snapshot) => {
            const downloadURL = await getDownloadURL(snapshot.ref);
            midia.links.push(downloadURL);
          });
        }
        uploadMidia(OSResponse.id, midia);
      }



      toast.success("Ordem de serviço criada com sucesso!");
      navigate("/");

    } catch (error) {
      toast.error("Erro ao criar ordem de serviço:" + error);
    }
  };

  return (
    <div className="w-screen-md mx-auto">
      <div className="mt-4 p-4 bg-gray-100">
        <OSForm
          setarOS={setarOS}
          adicionarImagens={adicionarImagens}
          setMidiaDesc={setMidiaDesc}
        />
        <OrcamentoForm setarReserva={setarReserva} />
      </div>
      <hr />
      <div className=" flex items-center justify-end gap-x-6 p-8 bg-gray-100">
        <button
          type="button"
          className="text-sm font-semibold leading-6 text-gray-900"
          onClick={() => {
            navigate("/");
          }}
        >
          Cancelar
        </button>
        <button
          onClick={envia}
          type="submit"
          className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
        >
          Salvar
        </button>
      </div>
    </div>
  );
}

function gerarNumeroAleatorio(): number {
  return Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000;
}
