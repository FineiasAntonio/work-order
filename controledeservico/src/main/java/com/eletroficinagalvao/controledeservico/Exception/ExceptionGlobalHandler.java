package com.eletroficinagalvao.controledeservico.Exception;

import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.BadRequestException;
import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.ExceptionDTO;
import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.InternalServerErrorException;
import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.NotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ExceptionGlobalHandler{

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionDTO> handleNotFoundException(NotFoundException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> handleBadRequestException(BadRequestException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionDTO> handleInternalServerErrorException(InternalServerErrorException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}