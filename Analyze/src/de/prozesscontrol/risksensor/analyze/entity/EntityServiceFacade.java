package de.prozesscontrol.risksensor.analyze.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityServiceFacade {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Risksensor");

    public EntityServiceFacade() {
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Object mergeEntity(Object entity) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                entity = em.merge(entity);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    entity = null;
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return entity;
    }

    public Object persistEntity(Object entity) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                em.persist(entity);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    entity = null;
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return entity;
    }

    /** <code>select o from TRsData o</code> */
    public List<TRsData> queryTRsDataFindAll() {
        return getEntityManager().createNamedQuery("TRsData.findAll").getResultList();
    }

    /** <code>select o from TRsData o</code> */
    public List<TRsData> queryTRsDataFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsData.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsData(TRsData TRsData) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsData =
                        em.find(TRsData.class, new TRsDataPK(TRsData.getCreatedon(), TRsData.getDeviceid(), TRsData.getEventid(),
                                                             TRsData.getRecordnum(), TRsData.getSdcardid()));
                em.remove(TRsData);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /** <code>select o from TRsEvent o</code> */
    public List<TRsEvent> queryTRsEventFindAll() {
        return getEntityManager().createNamedQuery("TRsEvent.findAll").getResultList();
    }

    /** <code>select o from TRsEvent o</code> */
    public List<TRsEvent> queryTRsEventFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsEvent.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsEvent(TRsEvent TRsEvent) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsEvent = em.find(TRsEvent.class, TRsEvent.getId());
                em.remove(TRsEvent);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /** <code>select o from TRsSdcard o</code> */
    public List<TRsSdcard> queryTRsSdcardFindAll() {
        return getEntityManager().createNamedQuery("TRsSdcard.findAll").getResultList();
    }

    /** <code>select o from TRsSdcard o</code> */
    public List<TRsSdcard> queryTRsSdcardFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsSdcard.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsSdcard(TRsSdcard TRsSdcard) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsSdcard = em.find(TRsSdcard.class, TRsSdcard.getSdcardInfo());
                em.remove(TRsSdcard);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /** <code>select o from TRsEventfactor o</code> */
    public List<TRsEventfactor> queryTRsEventfactorFindAll() {
        return getEntityManager().createNamedQuery("TRsEventfactor.findAll").getResultList();
    }

    /** <code>select o from TRsEventfactor o</code> */
    public List<TRsEventfactor> queryTRsEventfactorFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsEventfactor.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsEventfactor(TRsEventfactor TRsEventfactor) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsEventfactor = em.find(TRsEventfactor.class, TRsEventfactor.getId());
                em.remove(TRsEventfactor);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /** <code>select o from TRsLpn o</code> */
    public List<TRsLpn> queryTRsLpnFindAll() {
        return getEntityManager().createNamedQuery("TRsLpn.findAll").getResultList();
    }

    /** <code>select o from TRsLpn o</code> */
    public List<TRsLpn> queryTRsLpnFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsLpn.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsLpn(TRsLpn TRsLpn) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsLpn = em.find(TRsLpn.class, TRsLpn.getDeviceid());
                em.remove(TRsLpn);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /** <code>select o from TRsTour o</code> */
    public List<TRsTour> queryTRsTourFindAll() {
        return getEntityManager().createNamedQuery("TRsTour.findAll").getResultList();
    }

    /** <code>select o from TRsTour o</code> */
    public List<TRsTour> queryTRsTourFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsTour.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsTour(TRsTour TRsTour) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsTour = em.find(TRsTour.class, TRsTour.getId());
                em.remove(TRsTour);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /** <code>select o from TRsCmd o</code> */
    public List<TRsCmd> queryTRsCmdFindAll() {
        return getEntityManager().createNamedQuery("TRsCmd.findAll").getResultList();
    }

    /** <code>select o from TRsCmd o</code> */
    public List<TRsCmd> queryTRsCmdFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsCmd.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsCmd(TRsCmd TRsCmd) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsCmd = em.find(TRsCmd.class, TRsCmd.getId());
                em.remove(TRsCmd);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**<code>select o from TRsTourLog o</code>
     */
    public List<TRsTourLog> queryTRsTourLogFindAll() {
        return getEntityManager().createNamedQuery("TRsTourLog.findAll").getResultList();
    }

    /**<code>select o from TRsTourLog o</code>
     */
    public List<TRsTourLog> queryTRsTourLogFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsTourLog.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsJourneyLog(TRsTourLog TRsJourneyLog) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsJourneyLog = em.find(TRsTourLog.class, TRsJourneyLog.getId());
                em.remove(TRsJourneyLog);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /** <code>select o from TRsTourData o order by o.createdon asc, o.recordnum asc</code> */
    public List<TRsTourData> queryTRsTourDataFindAll() {
        return getEntityManager().createNamedQuery("TRsTourData.findAll").getResultList();
    }

    /** <code>select o from TRsTourData o order by o.createdon asc, o.recordnum asc</code> */
    public List<TRsTourData> queryTRsTourDataFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsTourData.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsTourData(TRsTourData TRsTourData) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsTourData =
                        em.find(TRsTourData.class, new TRsTourDataPK(TRsTourData.getCreatedon(), TRsTourData.getEventid(),
                                                                     TRsTourData.getRecordnum(),
                                                                     TRsTourData.getTourid()));
                em.remove(TRsTourData);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /** <code>select o from TRsThreshold o</code> */
    public List<TRsThreshold> queryTRsThresholdFindAll() {
        return getEntityManager().createNamedQuery("TRsThreshold.findAll").getResultList();
    }

    /** <code>select o from TRsThreshold o</code> */
    public List<TRsThreshold> queryTRsThresholdFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsThreshold.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsThreshold(TRsThreshold TRsThreshold) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsThreshold =
                        em.find(TRsThreshold.class, new TRsThresholdPK(TRsThreshold.getCmdprefix(), TRsThreshold.getDeviceid(),
                                                                       TRsThreshold.getValidfrom()));
                em.remove(TRsThreshold);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /** <code>select o from TRsTourState o</code> */
    public List<TRsTourState> queryTRsTourStateFindAll() {
        return getEntityManager().createNamedQuery("TRsTourState.findAll").getResultList();
    }

    /** <code>select o from TRsTourState o</code> */
    public List<TRsTourState> queryTRsTourStateFindAllByRange(int firstResult, int maxResults) {
        Query query = getEntityManager().createNamedQuery("TRsTourState.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /** <code>select o from TRsTourState o where o.sdcardInfo.deviceId = :deviceId</code> */
    public List<TRsTourState> queryTRsTourStateFindByDeviceId(Object deviceId) {
        return getEntityManager().createNamedQuery("TRsTourState.findByDeviceId").setParameter("deviceId",
                                                                                               deviceId).getResultList();
    }

    /** <code>select o from TRsTourState o where o.sdcardInfo.deviceId = :deviceId</code> */
    public List<TRsTourState> queryTRsTourStateFindByDeviceIdByRange(Object deviceId, int firstResult,
                                                                     int maxResults) {
        Query query =
            getEntityManager().createNamedQuery("TRsTourState.findByDeviceId").setParameter("deviceId", deviceId);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /** <code>select o from TRsTourState o where o.sdcardInfo.sdcardId = :sdcardId</code> */
    public List<TRsTourState> queryTRsTourStateFindBySdcardId(Object sdcardId) {
        return getEntityManager().createNamedQuery("TRsTourState.findBySdcardId").setParameter("sdcardId",
                                                                                               sdcardId).getResultList();
    }

    /** <code>select o from TRsTourState o where o.sdcardInfo.sdcardId = :sdcardId</code> */
    public List<TRsTourState> queryTRsTourStateFindBySdcardIdByRange(Object sdcardId, int firstResult,
                                                                     int maxResults) {
        Query query =
            getEntityManager().createNamedQuery("TRsTourState.findBySdcardId").setParameter("sdcardId", sdcardId);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /** <code>select o from TRsTourState o where o.sdcardInfo.deviceId = :deviceId and o.sdcardInfo.sdcardId = :sdcardId</code> */
    public List<TRsTourState> queryTRsTourStateFindByDeviceIdSdcardId(Object deviceId, Object sdcardId) {
        return getEntityManager().createNamedQuery("TRsTourState.findByDeviceIdSdcardId").setParameter("deviceId",
                                                                                                       deviceId).setParameter("sdcardId",
                                                                                                                              sdcardId).getResultList();
    }

    /** <code>select o from TRsTourState o where o.sdcardInfo.deviceId = :deviceId and o.sdcardInfo.sdcardId = :sdcardId</code> */
    public List<TRsTourState> queryTRsTourStateFindByDeviceIdSdcardIdByRange(Object deviceId, Object sdcardId,
                                                                             int firstResult, int maxResults) {
        Query query =
            getEntityManager().createNamedQuery("TRsTourState.findByDeviceIdSdcardId").setParameter("deviceId",
                                                                                                    deviceId).setParameter("sdcardId",
                                                                                                                           sdcardId);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public void removeTRsTourState(TRsTourState TRsTourState) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                TRsTourState = em.find(TRsTourState.class, TRsTourState.getSdcardInfo());
                em.remove(TRsTourState);
                et.commit();
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

}
