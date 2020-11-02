package com.payment.controller;

import com.payment.client.ClientResolver;
import com.payment.dto.payment.PaymentAmountResponseDto;
import com.payment.dto.payment.PaymentCancellationResponseDto;
import com.payment.dto.payment.PaymentRequestDto;
import com.payment.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@Api(value = "payment")
@RequestMapping(value = "/api/payment")
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    private PaymentService paymentService;
    private ClientResolver clientResolver;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create payment", notes = "Creates payment data")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayment(
            @ApiParam(value = "Payment Data", required = true) @Valid @RequestBody final PaymentRequestDto paymentRequestDto) {
        LOGGER.info("Create payment service is called. User country: {}", clientResolver.getClientCountry());

        paymentService.createPayment(paymentRequestDto);
    }

    @RequestMapping(path = "/{paymentId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get payment by id", notes = "Return paymentId ant cancellation fee")
    public PaymentCancellationResponseDto getPaymentWithCancellationFee(@PathVariable Long paymentId) {
        LOGGER.info("Get payment by id service is called. User country: {}", clientResolver.getClientCountry());

        return paymentService.getPaymentWithCancellationFee(paymentId);
    }

    @RequestMapping(path = "/payments-sorted", method = RequestMethod.GET)
    @ApiOperation(value = "Get payments ordered by chosen sort direction and sort parameter", notes = "Return all not cancelled" +
            " payments ordered by chosen sort direction and sort parameter. Default sort direction: asc. Default sort parameter: amount")
    public List<PaymentAmountResponseDto> getNotCanceledPayments(@RequestParam(name = "Sort Direction") Sort.Direction sortDirection,
                                                                 @RequestParam(name = "Sort Parameter", defaultValue = "amount") String sortParameter) {
        LOGGER.info("Get payments ordered by chosen sort direction and sort parameter service is called. User country: {}", clientResolver.getClientCountry());

        return paymentService.getNotCanceledPayments(sortDirection, sortParameter);
    }

    @RequestMapping(path = "/cancel/{paymentId}", method = RequestMethod.PUT)
    @ApiOperation(value = "Cancel payment by id", notes = "Cancels payment by id")
    public void cancelPayment(@PathVariable Long paymentId) {
        LOGGER.info("Cancel payment by id service is called. User country: {}", clientResolver.getClientCountry());

        paymentService.cancelPayment(paymentId);
    }

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    public void setClientResolver(ClientResolver clientResolver) {
        this.clientResolver = clientResolver;
    }

}
