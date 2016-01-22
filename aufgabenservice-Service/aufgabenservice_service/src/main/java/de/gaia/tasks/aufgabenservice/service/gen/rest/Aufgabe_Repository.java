package de.gaia.tasks.aufgabenservice.service.gen.rest;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;

import de.muenchen.service.TenantService;

import de.gaia.tasks.aufgabenservice.service.gen.domain.Prioritaet_;
import de.gaia.tasks.aufgabenservice.service.gen.domain.Aufgabe_;

/**
* Provides a Repository for a {@link Aufgabe_}. This Repository can be exported as a REST Resource.
* <p>
* The Repository handles CRUD Operations. Every Operation is secured and takes care of the tenancy.
* For specific Documentation on how the generated REST point behaves, please consider the Spring Data Rest Reference
* <a href="http://docs.spring.io/spring-data/rest/docs/current/reference/html/">here</a>.
* </p>
*/
@RepositoryRestResource(exported = true,
	path="aufgabes", collectionResourceRel="aufgabes")
@PreAuthorize("hasAuthority('AufgabenService_READ_Aufgabe')")
@EnableOAuth2Resource
public interface Aufgabe_Repository extends CrudRepository<Aufgabe_, Long> {
	
	/**
	 * Name for the specific cache.
	 */
	String CACHE = "AUFGABE_CACHE";
	
	/**
	 * Get all the Aufgabe_ entities that match the current users tenancy.
	 * <p>
	 * Aufgabe_ entities that belong to another tenant will get filtered out.
	 * </p>
	 *
	 * @return an Iterable of the Aufgabe_ entities with the same Tenancy.
	 */
	@Override
	@PostFilter(TenantService.IS_TENANT_FILTER)
	Iterable<Aufgabe_> findAll();
	
	/**
	 * Get one specific Aufgabe_ by its unique oid.
	 * <p>
	 * If the tenant id does not match, no Permission on this resource will be granted.
	 * </p>
	 *
	 * @param oid The identifier of the Aufgabe_.
	 * @return The Aufgabe_ with the requested oid.
	 */
	@Override
	@Cacheable(value = CACHE, key = "#p0")
	@PostAuthorize(TenantService.IS_TENANT_AUTH)
	Aufgabe_ findOne(Long oid);
	
	/**
	 * Create or update a Aufgabe_.
	 * <p>
	 * If the oid already exists, the Aufgabe_ will be overridden, hence update.
	 * If the oid does no already exist, a new Aufgabe_ will be created, hence create.
	 * </p>
	 * <p>
	 * The Aufgabe_ can only be saved if the tenant matches the tenant of the current User.
	 * </p>
	 *
	 * @param aufgabe The Aufgabe_ that will be saved.
	 * @return the saved Aufgabe_.
	 */
	@SuppressWarnings("unchecked")
	@Override
	@CachePut(value = CACHE, key = "#p0.oid")
	@PreAuthorize("hasAuthority('AufgabenService_WRITE_Aufgabe')")
	Aufgabe_ save(Aufgabe_ aufgabe);
	
	/**
	 * Delete the Aufgabe_ by a specified oid.
	 * <p>
	 * The Aufgabe_ can only be deleted if the tenant matches.
	 * </p>
	 *
	 * @param oid the unique oid of the Aufgabe_ that will be deleted.
	 */
	@Override
	@CacheEvict(value = CACHE, key = "#p0")
	@PreAuthorize("hasAuthority('AufgabenService_DELETE_Aufgabe')")
	@PostAuthorize(TenantService.IS_TENANT_AUTH)
	void delete(Long oid);
	
	/**
	 * Delete a Aufgabe_ by entity.
	 * <p>
	 * The delete is only permitted if the tenant is matching.
	 * </p>
	 *
	 * @param entity The Aufgabe_ that will be deleted.
	 */
	@Override
	@CacheEvict(value = CACHE, key = "#p0.oid")
	@PreAuthorize("hasAuthority('AufgabenService_DELETE_Aufgabe')")
	@PostAuthorize(TenantService.IS_TENANT_AUTH)
	void delete(Aufgabe_ entity);
	
	/**
	 * Delete multiple Aufgabe_ entities by their oid.
	 * <p>
	 * Only the Aufgabe_ entities with matching tenant will be deleted.
	 * </p>
	 *
	 * @param entities The Iterable of Aufgabe_ entities that will be deleted.
	 */
	@Override
	@CacheEvict(value = CACHE, allEntries = true)
	@PreAuthorize("hasAuthority('AufgabenService_DELETE_Aufgabe')")
	@PreFilter(TenantService.IS_TENANT_FILTER)
	void delete(Iterable<? extends Aufgabe_> entities);
	
	/**
	 * Delete all Aufgabe_ entities.
	 * <p>
	 * Only the Aufgabe_ entities with matching tenant will be deleted.
	 * </p>
	 */
	@Override
	@CacheEvict(value = CACHE, allEntries = true)
	@PreAuthorize("hasAuthority('AufgabenService_DELETE_Aufgabe')")
	@PreFilter(TenantService.IS_TENANT_FILTER)
	void deleteAll();
	
	Aufgabe_ findByBeschreibung(@Param(value= "beschreibung") String beschreibung);
	Aufgabe_ findByPrioritaet(@Param(value= "prioritaet") Prioritaet_ prioritaet);
	Aufgabe_ findByFaelligAm(@Param(value= "faelligAm") java.util.Date faelligAm);
	List<Aufgabe_> findAufgabeByBeschreibung(@Param("beschreibung") String beschreibung);
	List<Aufgabe_> findAufgabeByPrioritaet(@Param("prioritaet") String prioritaet);
	List<Aufgabe_> findAufgabeByFaelligAm(@Param("faelligAm") String faelligAm);
	
	/**
	 * Find the Aufgabe_ entities with a bearbeiter relation to the Mitarbeiter with the given oid.
	 * @param oid the unique oid of the Mitarbeiter that will be searched for in the bearbeiter relation.
	 */
	java.util.Collection<Aufgabe_> findByBearbeiterOid(@Param(value = "oid") Long oid);
}
