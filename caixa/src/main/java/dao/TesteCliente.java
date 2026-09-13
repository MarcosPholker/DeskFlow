package dao;

import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Produto;

public class TesteCliente {

    public static void main(String[] args) {

        
        Cliente cliente = new Cliente(
                "Marcos",
                "marcos@email.com",
                "11999999999"
        );

        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.inserirCliente(cliente);
        
        Produto produtos = new Produto("Caneta", 2.50, 10);


        // Criamos o DAO responsável pelos Produtos
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // Salvamos todos os produtos da lista no banco
        produtoDAO.addProduto(produtos);
        
        Pedido pedido = new Pedido();

        pedido.setCliente(cliente);

        ItemPedido itemPedido = new ItemPedido();

        itemPedido.setPedido(pedido);

        itemPedido.setProduto(produtos);


        // ============================================================
        // 5 - COLOCANDO O ITEM DENTRO DO PEDIDO
        // ============================================================

        // Criamos uma lista de ItemPedido
        List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();

        // Colocamos nosso ItemPedido dentro da lista
        itensPedido.add(itemPedido);

        // Agora dizemos que o Pedido possui essa lista de itens
        //
        // pedido.itemPedido = itensPedido
        pedido.setItemPedido(itensPedido);


        // ============================================================
        // 6 - SALVANDO O PEDIDO
        // ============================================================

        // Criamos o DAO responsável pelo Pedido
        PedidoDAO pedidoDAO = new PedidoDAO();

        // Agora salvamos o Pedido
        //
        // O Cliente já existe no banco, então o Pedido
        // consegue utilizar o ID dele como cliente_id.
        pedidoDAO.inserirDados(pedido);


        // ============================================================
        // 7 - SALVANDO O ITEM DO PEDIDO
        // ============================================================

        // Criamos o DAO responsável pelo ItemPedido
        ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();

        // Agora salvamos o ItemPedido
        //
        // O Pedido já foi salvo anteriormente,
        // então o ItemPedido consegue utilizar o ID do Pedido.
        itemPedidoDAO.addItemPedido(itensPedido);


        // ============================================================
        // RESUMO DA ORDEM
        // ============================================================
        //
        // Cliente
        //    ↓
        // salva primeiro
        //
        // Produto
        //    ↓
        // salva primeiro
        //
        // Pedido
        //    ↓
        // aponta para o Cliente
        //
        // ItemPedido
        //    ↓
        // aponta para o Pedido
        //    ↓
        // possui os Produtos
        //
        // ============================================================

        System.out.println(pedido);
    }
}
