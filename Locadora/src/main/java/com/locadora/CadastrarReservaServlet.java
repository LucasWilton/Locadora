package com.locadora;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CadastrarReservaServlet extends HttpServlet {
    static List<Reserva> reservas = new ArrayList<>();
    private static List<Cliente> clientes = CadastrarClienteServlet.clientes;
    private static List<Veiculo> veiculos = CadastrarVeiculoServlet.veiculos;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clienteNome = request.getParameter("cliente");
        String veiculoPlaca = request.getParameter("veiculo");
        String dataInicio = request.getParameter("dataInicio");
        String dataFim = request.getParameter("dataFim");

        
        Cliente cliente = clientes.stream()
                .filter(c -> c.getNome().equals(clienteNome))
                .findFirst()
                .orElse(null);

       
        Veiculo veiculo = veiculos.stream()
                .filter(v -> v.getPlaca().equals(veiculoPlaca))
                .findFirst()
                .orElse(null);

        if (cliente == null || veiculo == null) {
           
            request.setAttribute("mensagem", "Cliente ou veículo não cadastrado.");
            request.getRequestDispatcher("/reservar.html").forward(request, response);
            return;
        }

        
        boolean veiculoReservado = reservas.stream()
                .anyMatch(r -> r.getVeiculo().equals(veiculoPlaca) &&
                        ((dataInicio.compareTo(r.getDataFim()) <= 0) && (dataFim.compareTo(r.getDataInicio()) >= 0)));

        if (veiculoReservado) {
            
            request.setAttribute("mensagemErro", "O veículo com a placa " + veiculoPlaca + " já está reservado no período selecionado.");
            request.getRequestDispatcher("/erro_reserva.html").forward(request, response);
        } else {
            
            Reserva reserva = new Reserva(clienteNome, veiculoPlaca, dataInicio, dataFim);
            reservas.add(reserva);

            
            response.sendRedirect("reservas");
        }
    }
}