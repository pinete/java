package com.example.obhibernateproyecto.dao;

import com.example.obhibernateproyecto.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

// Lo podíamos hacer con session perfectamente.
// Lo hacemos con Criteria porque si el día de mañana cambiamos el proveedor de persistencia (por ejemplo, a Eclipse Link, u otro),
// el código no va a cambiar.

@Repository // Indicamos a spring que esto es una clase que gestiona datos (interactúa con una DB)
public class UserDAOImpl implements UserDAO {

    /* NOTA
    Persistence Context: Es una especie de caché de primer nivel donde todas las entidades se recuperan de la base de datos o se guardan en ella.
    Actúa como un intermediario entre nuestra aplicación y el almacenamiento persistente. El Persistence Context realiza un seguimiento de los cambios
    realizados en una entidad administrada. Si algo cambia durante una transacción, la entidad se marca como “sucia” (es decir, modificada).

    Facilita la gestión de transacciones y la manipulación de entidades sin que el programador tenga que preocuparse por los detalles de la administración
    del ciclo de vida del EntityManager
     */
    /* NOTA 2
    EntityManager: El EntityManager es la interfaz principal para interactuar con el Persistence Context. Permite realizar operaciones CRUD
    (crear, leer, actualizar y eliminar) en las entidades. El EntityManager se crea a partir del Persistence Context.
     */

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Devuelve una lista con todos los usuarios que están activos
     * @return ListaUsuariosActivos
     */
    @Override
    public List<User> findAllActive(){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> userTable = criteria.from(User.class);

        Predicate isActive = builder.isTrue(userTable.get("active"));
        criteria.select(userTable).where(isActive);
        // List<User> result = entityManager.createQuery(criteria).getResultList();
        return entityManager.createQuery(criteria).getResultList();
    }
}
