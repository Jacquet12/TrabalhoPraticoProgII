package br.edu.projeto.controller;

public class ValidadorCPF {
    public static boolean isCPF(String cpf) {
        cpf = cpf.replace(".", "").replace("-", "");
        if (cpf.length() != 11)
            return false;

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int digitoVerificador1 = 11 - (soma % 11);
        if (digitoVerificador1 > 9) digitoVerificador1 = 0;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        int digitoVerificador2 = 11 - (soma % 11);
        if (digitoVerificador2 > 9) digitoVerificador2 = 0;

        return (digitoVerificador1 == Character.getNumericValue(cpf.charAt(9)) &&
                digitoVerificador2 == Character.getNumericValue(cpf.charAt(10)));
    }
}


