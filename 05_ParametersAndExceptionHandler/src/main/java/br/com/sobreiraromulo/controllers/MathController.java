package br.com.sobreiraromulo.controllers;

import br.com.sobreiraromulo.exceptions.UnsupportedMathOperationException;
import br.com.sobreiraromulo.math.SimpleMath;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.sobreiraromulo.request.conveters.NumberConverter.convertToDouble;
import static br.com.sobreiraromulo.request.conveters.NumberConverter.isNumeric;


@RestController
@RequestMapping("math")
public class MathController {

    private final SimpleMath math = new SimpleMath();


    @RequestMapping("sum/{numberOne}/{numberTwo}")
    public double sum(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) {
        if(isNumeric(numberOne) || isNumeric(numberTwo)) throw  new UnsupportedMathOperationException("Please set a numeric value!");

        return math.sum(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @RequestMapping("sub/{numberOne}/{numberTwo}")
    public double sub(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) {
        if(isNumeric(numberOne) || isNumeric(numberTwo)) throw  new UnsupportedMathOperationException("Please set a numeric value!");

        return math.subtraction(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @RequestMapping("multi/{numberOne}/{numberTwo}")
    public double multi(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) {
        if(isNumeric(numberOne) || isNumeric(numberTwo)) throw  new UnsupportedMathOperationException("Please set a numeric value!");

        return math.multiplication(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @RequestMapping("div/{numberOne}/{numberTwo}")
    public double div(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) {
        if(isNumeric(numberOne) || isNumeric(numberTwo)) throw  new UnsupportedMathOperationException("Please set a numeric value!");

        if (convertToDouble(numberTwo) <= 0) throw new UnsupportedMathOperationException("Number two must be more than 0.");

        return math.division(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @RequestMapping("mean/{numberOne}/{numberTwo}")
    public double mean(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) {
        if(isNumeric(numberOne) || isNumeric(numberTwo)) throw  new UnsupportedMathOperationException("Please set a numeric value!");

        return math.mean(convertToDouble(numberOne),  convertToDouble(numberTwo));
    }

    @RequestMapping("squareroot/{number}")
    public double squareroot(
            @PathVariable("number") String number
    ) {
        if(isNumeric(number)) throw  new UnsupportedMathOperationException("Please set a numeric value!");

        return math.squareRoot(convertToDouble(number));
    }


}
