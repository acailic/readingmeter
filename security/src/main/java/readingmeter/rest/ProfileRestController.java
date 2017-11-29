package readingmeter.rest;

import readingmeter.Fraction;
import readingmeter.Profile;
import readingmeter.exceptions.EntityNotFoundException;
import readingmeter.exceptions.UserNotFoundException;
import readingmeter.repositories.AccountRepository;
import readingmeter.repositories.ProfileRepository;
import readingmeter.validators.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profiles")
public class ProfileRestController {

	private final ProfileRepository profileRepository;

	private final AccountRepository accountRepository;

	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.setValidator(new ProfileValidator());
	}
	@Autowired
	ProfileRestController(ProfileRepository profileRepository,
						  AccountRepository accountRepository) {
		this.profileRepository = profileRepository;
		this.accountRepository = accountRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	Resources<ProfileResource> readProfiles(Principal principal) {
		this.validateUser(principal);

		List<ProfileResource> profilResourceList = this.profileRepository
			.findByAccountUsername(principal.getName()).stream()
			.map(ProfileResource::new)
			.collect(Collectors.toList());
		return new Resources<>(profilResourceList);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(Principal principal, @Valid @RequestBody Profile profileInput) throws ValidationException {
		this.validateUser(principal);
		this.validateProfile(profileInput);
		return accountRepository
				.findByUsername(principal.getName())
				.map(account -> {
					Profile profile = profileRepository.save(
							new Profile(account, profileInput.getName(), profileInput.getFractions(), profileInput.getUri()));
					Link forOneProfile = new ProfileResource(profile).getLink(Link.REL_SELF);

					return ResponseEntity.created(URI
						.create(forOneProfile.getHref()))
						.build();
				})
				.orElse(ResponseEntity.noContent().build());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{profileId}")
	ProfileResource readProfile(Principal principal, @PathVariable Long profileId) {
		this.validateUser(principal);
		this.validateEntity(profileId);
		return new ProfileResource(
			this.profileRepository.findOne(profileId));
	}

	private void validateUser(Principal principal) {
		String userId = principal.getName();
		this.accountRepository
				.findByUsername(userId)
				.orElseThrow(
						() -> new UserNotFoundException(userId));
	}

	private void validateEntity(Long profileId) {
		 if(!profileRepository
				.exists(profileId)){
				throw new EntityNotFoundException(profileId.toString());
		 }
	}

	private void validateProfile(Profile profile)  throws  ValidationException {
		//profile
        if(profile.getFractions() == null || profile.getFractions().size()!=12){
            throw new ValidationException("Size of fractions for profile need to be 12.");
        }
        if(profile.getFractions().stream().mapToDouble(Fraction::getFraction).sum()!=1){
            throw new ValidationException("Sum of fractions for profile need to be 1.");
        }
		if (profile.getFractions().stream()
				.map(fraction -> fraction.getMonth().getValue()).collect(Collectors.toSet()).size()!=12) {
			throw new ValidationException("Months need to be unique.");
		}
	}
}
