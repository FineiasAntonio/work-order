import ReactModal from "react-modal";
import { Produto } from "../../Service/Entities/Produto";

interface ModalProps {
    Produto: Produto
    isOpen: boolean;
    onClose: () => void;
}

export default function ModalViewProduto({Produto, isOpen, onClose}: ModalProps){
    return (
    <ReactModal
        isOpen={isOpen}
        onRequestClose={onClose}
        contentLabel="Detalhes da Ordem de Serviço"
    >

        
        
    </ReactModal>
    )
}