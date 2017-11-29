/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package readingmeter.rest;

import readingmeter.Profile;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

class ProfileResource extends ResourceSupport {

	private final Profile profile;

	public ProfileResource(Profile profile) {
		String username = profile.getAccount().getUsername();
		this.profile = profile;
		this.add(new Link(profile.uri, "profile-uri"));
		this.add(linkTo(ProfileRestController.class, username).withRel("profiles"));
		this.add(linkTo(
				methodOn(ProfileRestController.class, username).readProfile(null,
						profile.getId())).withSelfRel());
	}

	public Profile getProfile() {
		return profile;
	}
}