package com.eletroficinagalvao.controledeservico.Domain.Service;

import com.eletroficinagalvao.controledeservico.Domain.Entity.*;
import com.eletroficinagalvao.controledeservico.Infra.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Infra.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Infra.Entity.ServicoSituacao;
import com.eletroficinagalvao.controledeservico.Infra.Entity.SubSituacao;
import com.eletroficinagalvao.controledeservico.Infra.Repository.OSRepository;
import com.eletroficinagalvao.controledeservico.Infra.Repository.ProdutoRepository;
import com.eletroficinagalvao.controledeservico.Infra.Repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class Schedule {

    private ProdutoRepository produtoRepository;
    private OSRepository osRepository;
    private ReservaRepository reservaRepository;

//    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.MINUTES)
    public void updateInfos(){
        updateProductsValues();
        updateOrderStatus();
        log.info("Updated infos");
    }

    private void updateProductsValues() {

        reservaRepository.findByAtivo(true).forEach(reserva -> {
            reserva.getProdutos_reservados().forEach(produto -> {
                Produto produtoDoEstoque = produtoRepository.findById(produto.getId()).get();
                BeanUtils.copyProperties(produtoDoEstoque, produto, "quantidade", "quantidadeNescessaria");
            });
            reservaRepository.save(reserva);
        });

    }

    private void updateOrderStatus() {

        osRepository.findBySituacao(ServicoSituacao.AGUARDANDO_PECA).stream()
                .filter(x -> x.getReserva().getProdutos_reservados().stream()
                        .allMatch(e -> e.getQuantidade() == e.getQuantidadeNescessaria()))
                .forEach(x -> {

                    x.setSituacao(ServicoSituacao.EM_ANDAMENTO);
                    x.setSubSituacao(SubSituacao.AGUARDANDO_MONTAGEM);
                    osRepository.save(x);

                    // TODO: estudar queries do mongodb para fazer um update melhor
                    Reserva reserva = reservaRepository.findByIdOS(x.getId());
                    reserva.setAtivo(false);
                    reservaRepository.save(reserva);
                });

    }

}
