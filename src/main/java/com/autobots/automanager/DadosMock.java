package com.autobots.automanager;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.providers.AutenticacaoProvedor;
import com.autobots.automanager.repositorios.EmpresaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DadosMock implements CommandLineRunner {

  @Autowired
  private EmpresaRepository empresaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  @Override
  public void run(String... args) throws Exception {
    var admin = inserirUsuario();
    inserirEmpresa(admin);
  }

  public Empresa empresa = new Empresa();

  private Usuario inserirUsuario() {
    var usuario = new Usuario();
    var credencial = new Credencial();
    var email = new Email();
    var telefone = new Telefone();
    var documento = new Documento();
    var endereco = new Endereco();

    credencial.setNomeUsuario("admin-usuario");
    credencial.setSenha("123456");

    documento.setTipo(TipoDocumento.CPF);
    documento.setNumero("1234567890");
    documento.setDataEmissao(new Date());

    email.setEndereco("admin@autobots.com");

    endereco.setBairro("Bairro Exemplo");
    endereco.setCidade("Cidade Exemplo");
    endereco.setEstado("SP");
    endereco.setRua("Rua Exemplo");
    endereco.setNumero("123");
    endereco.setCodigoPostal("1234567890");
    endereco.setInformacoesAdicionais("Apto 123");

    telefone.setDdd("11");
    telefone.setNumero("999999999");

    usuario.setCredencial(credencial);
    usuario.setNome("Admin");
    usuario.setNomeSocial("Admin");
    usuario.setEmails(List.of(email));
    usuario.setTelefones(List.of(telefone));
    usuario.setDocumentos(List.of(documento));
    usuario.setEndereco(endereco);
    usuario.setPerfil(PerfilUsuario.ADMIN);
    usuario.setInativo(false);

    autenticacaoProvedor.registrar(usuario);

    return usuario;
  }

  private void inserirEmpresa(Usuario admin) {
    var endereco = new Endereco();
    var telefone = new Telefone();

    endereco.setEstado("Aichi");
    endereco.setCidade("Toyota City");
    endereco.setBairro("Toyota District");
    endereco.setRua("Toyota Street");
    endereco.setNumero("1");
    endereco.setCodigoPostal("471-8571");
    endereco.setInformacoesAdicionais("Apto 101");

    telefone.setDdd("11");
    telefone.setNumero("123654987");

    empresa.setRazaoSocial("Toyota Motor Corporation");
    empresa.setNomeFantasia("Toyota");
    empresa.setCadastro(LocalDate.of(2025, 1, 1));
    empresa.setEndereco(endereco);
    empresa.setTelefones(List.of(telefone));
    empresa.setUsuarios(List.of(admin));

    empresaRepositorio.save(empresa);
  }
}
