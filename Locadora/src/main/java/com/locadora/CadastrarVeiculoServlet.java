package com.locadora;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CadastrarVeiculoServlet extends HttpServlet {
    static List<Veiculo> veiculos = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String marca = request.getParameter("marca");
        String modelo = request.getParameter("modelo");
        int ano = Integer.parseInt(request.getParameter("ano"));
        String cor = request.getParameter("cor");
        String placa = request.getParameter("placa");

        // Verifica se já existe um veículo com a mesma placa
        boolean placaExistente = veiculos.stream()
                .anyMatch(v -> v.getPlaca().equalsIgnoreCase(placa));

        if (placaExistente) {
            // Se a placa já existir, redireciona para uma página de erro
            response.sendRedirect("erro?mensagem=Veiculo%20com%20placa%20ja%20cadastrada");
        } else {
            // Caso contrário, cadastra o novo veículo
            Veiculo veiculo = new Veiculo(marca, modelo, ano, cor, placa);
            veiculos.add(veiculo);
            response.sendRedirect("veiculos");
        }
    }
}
