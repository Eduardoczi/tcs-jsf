package br.com.tcs.treinamento.dao;

import br.com.tcs.treinamento.entity.Empresa;
import br.com.tcs.treinamento.entity.Pessoa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmpresaDAO {

    // Remova a anotação @Stateless e a injeção via @PersistenceContext
    private EntityManager em;
    private static EntityManagerFactory emf;

    // Construtor que cria manualmente o EntityManager
    public EmpresaDAO() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("myPU");
        }
        em = emf.createEntityManager();
    }

    // Método para cadastrar (inserir) uma nova Pessoa
    public void cadastrar(Empresa empresa) {
        try {
            em.getTransaction().begin();
            em.persist(empresa);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    // Método para buscar uma Pessoa pelo seu ID
    public Empresa buscarPorId(Long id) {
        return em.find(Empresa.class, id);
    }

    // Método para listar todas as Pessoas
    public List<Empresa> listar() {
        return em.createQuery("SELECT e FROM Empresa e", Empresa.class).getResultList();
    }

    // Método para atualizar os dados de uma Pessoa
    public Empresa atualizar(Empresa empresa) {
        try {
            em.getTransaction().begin();
            Empresa e = em.merge(empresa);
            em.getTransaction().commit();
            return e;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    // Método para excluir (remover) uma Pessoa
    public void excluir(Empresa empresa) {
        try {
            em.getTransaction().begin();
            em.remove(em.contains(empresa) ? empresa : em.merge(empresa));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}