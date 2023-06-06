/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AbstractFacade.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import java.util.List;
import javax.persistence.EntityManager;

public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    /**
     * Construye un AbstractFacade.
     *
     * @param _entityClass
     */
    public AbstractFacade(Class<T> _entityClass) {
        this.entityClass = _entityClass;
    }

    /**
     * Devuelve el EntityManager.
     *
     * @return
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Crea en la base de datos la entidad correspondiente.
     *
     * @param _entity
     */
    public void create(T _entity) {
        getEntityManager().persist(_entity);
    }

    /**
     * Edita en la base de datos la entidad correspondiente.
     *
     * @param _entity
     */
    public void edit(T _entity) {
        getEntityManager().merge(_entity);
    }

    /**
     * Elimina de la base de datos la entidad correspondiente.
     *
     * @param _entity
     */
    public void remove(T _entity) {
        getEntityManager().remove(getEntityManager().merge(_entity));
    }

    /**
     * Devuelve la instancia de la clase 'T' con identificador 'id'.
     *
     * @param _id
     * @return
     */
    public T find(Object _id) {
        return getEntityManager().find(entityClass, _id);
    }

    /**
     * Devuelve la lista de instancias de la clase 'T' de la BD.
     *
     * @return
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Devuelve la lista de instancias de la clase 'T' de la BD en un rango
     * determinado.
     *
     * @param _range
     * @return
     */
    public List<T> findRange(int[] _range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(_range[1] - _range[0] + 1);
        q.setFirstResult(_range[0]);
        return q.getResultList();
    }

    /**
     * Devuelve el número de instancias que hay de la clase 'T' en la BD.
     *
     * @return
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
