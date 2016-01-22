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

import de.gaia.tasks.aufgabenservice.service.gen.domain.Mitarbeiter_;

/**
* Provides a Repository for a {@link Mitarbeiter_}. This Repository can be exported as a REST Resource.
* <p>
* The Repository handles CRUD Operations. Every Operation is secured and takes care of the tenancy.
* For specific Documentation on how the generated REST point behaves, please consider the Spring Data Rest Reference
* <a href="http://docs.spring.io/spring-data/rest/docs/current/reference/html/">here</a>.
* </p>
*/
@RepositoryRestResource(exported = true,
	path="mitarbeiters", collectionResourceRel="mitarbeiters")
@PreAuthorize("hasAuthority('AufgabenService_READ_Mitarbeiter')")
@EnableOAuth2Resource
public interface Mitarbeiter_Repository extends CrudRepository<Mitarbeiter_, Long> {
	
	/**
	 * Name for the specific cache.
	 */
	String CACHE = "MITARBEITER_CACHE";
	
	/**
	 * Get all the Mitarbeiter_ entities that match the current users tenancy.
	 * <p>
	 * Mitarbeiter_ entities that belong to another tenant will get filtered out.
	 * </p>
	 *
	 * @return an Iterable of the Mitarbeiter_ entities with the same Tenancy.
	 */
	@Override
	@PostFilter(TenantService.IS_TENANT_FILTER)
	Iterable<Mitarbeiter_> findAll();
	
	/**
	 * Get one specific Mitarbeiter_ by its unique oid.
	 * <p>
	 * If the tenant id does not match, no Permission on this resource will be granted.
	 * </p>
	 *
	 * @param oid The identifier of the Mitarbeiter_.
	 * @return The Mitarbeiter_ with the requested oid.
	 */
	@Override
	@Cacheable(value = CACHE, key = "#p0")
	@PostAuthorize(TenantService.IS_TENANT_AUTH)
	Mitarbeiter_ findOne(Long oid);
	
	/**
	 * Create or update a Mitarbeiter_.
	 * <p>
	 * If the oid already exists, the Mitarbeiter_ will be overridden, hence update.
	 * If the oid does no already exist, a new Mitarbeiter_ will be created, hence create.
	 * </p>
	 * <p>
	 * The Mitarbeiter_ can only be saved if the tenant matches the tenant of the current User.
	 * </p>
	 *
	 * @param mitarbeiter The Mitarbeiter_ that will be saved.
	 * @return the saved Mitarbeiter_.
	 */
	@SuppressWarnings("unchecked")
	@Override
	@CachePut(value = CACHE, key = "#p0.oid")
	@PreAuthorize("hasAuthority('AufgabenService_WRITE_Mitarbeiter')")
	Mitarbeiter_ save(Mitarbeiter_ mitarbeiter);
	
	/**
	 * Delete the Mitarbeiter_ by a specified oid.
	 * <p>
	 * The Mitarbeiter_ can only be deleted if the tenant matches.
	 * </p>
	 *
	 * @param oid the unique oid of the Mitarbeiter_ that will be deleted.
	 */
	@Override
	@CacheEvict(value = CACHE, key = "#p0")
	@PreAuthorize("hasAuthority('AufgabenService_DELETE_Mitarbeiter')")
	@PostAuthorize(TenantService.IS_TENANT_AUTH)
	void delete(Long oid);
	
	/**
	 * Delete a Mitarbeiter_ by entity.
	 * <p>
	 * The delete is only permitted if the tenant is matching.
	 * </p>
	 *
	 * @param entity The Mitarbeiter_ that will be deleted.
	 */
	@Override
	@CacheEvict(value = CACHE, key = "#p0.oid")
	@PreAuthorize("hasAuthority('AufgabenService_DELETE_Mitarbeiter')")
	@PostAuthorize(TenantService.IS_TENANT_AUTH)
	void delete(Mitarbeiter_ entity);
	
	/**
	 * Delete multiple Mitarbeiter_ entities by their oid.
	 * <p>
	 * Only the Mitarbeiter_ entities with matching tenant will be deleted.
	 * </p>
	 *
	 * @param entities The Iterable of Mitarbeiter_ entities that will be deleted.
	 */
	@Override
	@CacheEvict(value = CACHE, allEntries = true)
	@PreAuthorize("hasAuthority('AufgabenService_DELETE_Mitarbeiter')")
	@PreFilter(TenantService.IS_TENANT_FILTER)
	void delete(Iterable<? extends Mitarbeiter_> entities);
	
	/**
	 * Delete all Mitarbeiter_ entities.
	 * <p>
	 * Only the Mitarbeiter_ entities with matching tenant will be deleted.
	 * </p>
	 */
	@Override
	@CacheEvict(value = CACHE, allEntries = true)
	@PreAuthorize("hasAuthority('AufgabenService_DELETE_Mitarbeiter')")
	@PreFilter(TenantService.IS_TENANT_FILTER)
	void deleteAll();
	
	Mitarbeiter_ findByName(@Param(value= "name") String name);
	Mitarbeiter_ findByMail(@Param(value= "mail") String mail);
	Mitarbeiter_ findByGeburtsdatum(@Param(value= "geburtsdatum") java.util.Date geburtsdatum);
	List<Mitarbeiter_> findMitarbeiterByName(@Param("name") String name);
	List<Mitarbeiter_> findMitarbeiterByMail(@Param("mail") String mail);
	List<Mitarbeiter_> findMitarbeiterByGeburtsdatum(@Param("geburtsdatum") String geburtsdatum);
	
}
