package com.workordercontrol.api.Domain.Service;

import com.workordercontrol.api.Infra.Repository.ReservaRepository;
import com.workordercontrol.api.Infra.Repository.StorageRepository;
import com.workordercontrol.api.Infra.Repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Service
@Aspect
@Log4j2
@RequiredArgsConstructor
public class ScheduleAspect {

    private StorageRepository produtoRepository;
    private WorkOrderRepository osRepository;
    private ReservaRepository reservaRepository;

    @After(
            "execution(* com.eletroficinagalvao.controledeservico.Service.*.create()) || " +
            "execution(* com.eletroficinagalvao.controledeservico.Service.*.update()) ||" +
            "execution(* com.eletroficinagalvao.controledeservico.Service.*.delete())"
    )
    public void updateInfos(){
        updateProductsValues();
        updateOrderStatus();
        log.info("Updated infos");
    }

    private void updateProductsValues() {
//
//        reservaRepository.findByAtivo(true).forEach(reserva -> {
//            reserva.getReservedProducts().forEach(produto -> {
//                Product produtoDoEstoque = produtoRepository.findById(produto.).get();
//                BeanUtils.copyProperties(produtoDoEstoque, produto, "quantidade", "quantidadeNescessaria");
//            });
//            reservaRepository.save(reserva);
//        });

    }

    private void updateOrderStatus() {

//        osRepository.findBySituacao(Status.AGUARDANDO_PECA).stream()
//                .filter(x -> x.getReserva().getProdutos_reservados().stream()
//                        .allMatch(e -> e.getQuantidade() == e.getQuantidadeNescessaria()))
//                .forEach(x -> {
//
//                    x.setSituacao(Status.EM_ANDAMENTO);
//                    x.setSubSituacao(SubSituacao.AGUARDANDO_MONTAGEM);
//                    osRepository.save(x);
//
//                    // TODO: estudar queries do mongodb para fazer um update melhor
//                    Reserve reserva = reservaRepository.findByIdOS(x.getId());
//                    reserva.setAtivo(false);
//                    reservaRepository.save(reserva);
//                });

    }

}
