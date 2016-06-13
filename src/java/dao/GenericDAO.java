package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.lang.reflect.ParameterizedType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import util.ErroSistema;
import util.HibernateUtil;
import util.MessageUtil;

/**
 * @author lber Todas as operações de cadastrar, excluir, alterar e listar serão
 * implementadas por essa classe de uma forma genérica. DAO é responsável por
 * fazer as transações com o banco de dados.
 */
public class GenericDAO<E> {//Recebe um tipo genérico que vai ser interpretado como um tipo

    private Class<E> classe;
    private Transaction transacao = null;//Serve para controlar as transações como incluir, deletar, etc...
    private Session sessao = null;

    @SuppressWarnings("unchecked")
    public GenericDAO() {//precisa disso para fazer um listar genérico
        //(Class<E>) cast para converter para o tipo da classe que está sendo passada no diamante;
        //getClass() pega o tipo genérico da classe filha = getGenericSuperclass()
        //depois pega o tipo da classe filha e retorna para a this.classe 
        this.classe = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        //pega a classe atual, da classe atual pega qual o tipo genérico dele "usuario, software..." 
        //que é o objeto passado no diamante getActualTypeArguments()[0] 
        //ou seja pega o tipo do objeto passado no diamante [0] primeira posição
    }

    /*Método para salvar um objeto no banco*/
    public void salvar(E entidade) throws ErroSistema {
        //Captura uma sessão em aberto
        sessao = HibernateUtil.getSessionFactory().openSession();
        //vai tentar executar esse bloco de instruções.
        try {
            //Inicia uma tranzação na sessão atual
            transacao = sessao.beginTransaction();
            //Salva o objeto genérico que foi passádo por parâmetro
            sessao.save(entidade);
            //confirmar a transação
            transacao.commit();
        } catch (RuntimeException erro) {
            //Se der algum problema ele será desfeito no rollback em tempo de execução - RuntimeException
            if (transacao != null) {//verifica se a transação foi aberta != é porque foi aberta
                transacao.rollback(); //roll back = reverter
                MessageUtil.MensagemErro(""+erro);
            }
            /**
             * se a transação for aberta e ele cair no catch é porque deu erro,
             * então chama o rollback para reverter. e seta uma mensagem na
             * camada de tratamento de erro.
             */
            throw new ErroSistema("Erro ao tentar salvar!", erro);//repropaga o erro
        } finally {
            sessao.close();//Por fim, fecha a sessão independente de dar errou, ou não.
        }
    }

    /*Método para deletar um objeto. Necessita buscá-lo antes*/
    public void deletar(E entidade) throws ErroSistema {

        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            transacao = sessao.beginTransaction();
            sessao.delete(entidade);
            transacao.commit();
        } catch (RuntimeException erro) {
            if (transacao != null) {
                transacao.rollback();
            }
            throw new ErroSistema("Erro ao tentar deletar!", erro);
        } finally {
            sessao.close();
        }
    }

    /*Método para editar um objeto, retorna o objeto ao qual se deseja alterar.
    Necessita buscá-lo pelo método buscar**/
    public void editar(E entidade) throws ErroSistema {
        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            transacao = sessao.beginTransaction();
            sessao.update(entidade);
            transacao.commit();
        } catch (RuntimeException erro) {
            if (transacao != null) {
                transacao.rollback();
            }
            throw new ErroSistema("Erro ao tentar salvar!", erro);
        } finally {
            sessao.close();
        }
    }

    /*Método para retornar uma lista de objetos persistidos no banco*/
    public List<E> listar() throws ErroSistema {
        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            Criteria consulta = sessao.createCriteria(classe);//passa o objeto "classe" que vai ter o tipo de objeto passado
            List<E> resultado = consulta.list();
            return resultado;
        } catch (RuntimeException er) {
            throw new ErroSistema("Erro ao tentar consultar os dados", er);
        } finally {
            sessao.close();
        }
    }

    /*Função para auxiliar em operações como excluir e atualizar, pois antes
    é necessário buscar um elemento no banco para que seja possível fazer tal 
    operação*/
    public E buscar(Integer codigo) throws ErroSistema {
        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            Criteria consulta = sessao.createCriteria(classe);

            /**
             * Adiciona uma restrição à minha consulta, funciona como clausula
             * WHERE "Restrictions.idEq(classe)", ou seja, trará o resultado
             * onde o id do objeto passado for igual ao id da linha no banco
             */
            consulta.add(Restrictions.idEq(codigo));

            //uniqueResult() É usado quando se quer retornar apenas um elemento
            E resultado = (E) consulta.uniqueResult();
            return resultado;
        } catch (RuntimeException er) {
            throw new ErroSistema("Erro ao tentar consultar os dados", er);
        } finally {
            sessao.close();
        }
    }

    /*O método merge tanto serve para salvar quanto para atualizar. 
    Se o objeto não estiver contido no banco, o hibernate irá inserir, 
    se não, irá atualizar.*/
    public void merge(E entidade) throws ErroSistema {
        sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            transacao = sessao.beginTransaction();
            sessao.merge(entidade);
            transacao.commit();
        } catch (RuntimeException erro) {
            if (transacao != null) {
                transacao.rollback();
            }
            throw new ErroSistema("Erro ao tentar salvar!", erro);
        } finally {
            sessao.close();
        }
    }
}
