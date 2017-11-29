package readingmeter;

import readingmeter.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


// curl -X POST -vu android-readingmeter:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=password&username=divljac&grant_type=password&scope=write&client_secret=123456&client_id=android-readingmeter"
// curl -v POST http://127.0.0.1:8080/bookmarks -H "Authorization: Bearer <oauth_token>""

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	//// CORS FILTER
	@Bean
	FilterRegistrationBean corsFilter(
			@Value("${tagit.origin:http://localhost:9000}") String origin) {
		return new FilterRegistrationBean(new Filter() {
			public void doFilter(ServletRequest req, ServletResponse res,
					FilterChain chain) throws IOException, ServletException {
				HttpServletRequest request = (HttpServletRequest) req;
				HttpServletResponse response = (HttpServletResponse) res;
				String method = request.getMethod();
				// this origin value could just as easily have come from a database
				response.setHeader("Access-Control-Allow-Origin", origin);
				response.setHeader("Access-Control-Allow-Methods",
						"POST,GET,OPTIONS,DELETE");
				response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
				response.setHeader("Access-Control-Allow-Credentials", "true");
				response.setHeader(
						"Access-Control-Allow-Headers",
						"Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
				if ("OPTIONS".equals(method)) {
					response.setStatus(HttpStatus.OK.value());
				}
				else {
					chain.doFilter(req, res);
				}
			}

			public void init(FilterConfig filterConfig) {
			}

			public void destroy() {
			}
		});
	}

	@Bean
	CommandLineRunner init(AccountRepository accountRepository,
			MeterReadingRepository meterReadingRepository,
						   ProfileRepository profileRepository,
						   ConnectionRepository connectionRepository) {
		return (evt) -> Arrays.asList(
				"ilke,divljac,lisica,admin".split(","))
				.forEach(
						a -> {
							Account account = accountRepository.save(new Account(a,
									"password"));
							Set<Fraction> fractions = new HashSet();
							fractions.add(new Fraction(Month.JAN, 0.1  ));
							fractions.add(new Fraction(Month.FEB, 0.1  ));
							fractions.add(new Fraction(Month.MAR, 0.1  ));
							fractions.add(new Fraction(Month.APR, 0.1  ));
							fractions.add(new Fraction(Month.MAY, 0.1  ));
							fractions.add(new Fraction(Month.JUN, 0.0  ));
							fractions.add(new Fraction(Month.JUL, 0.0  ));
							fractions.add(new Fraction(Month.AUG, 0.1  ));
							fractions.add(new Fraction(Month.SEP, 0.1  ));
							fractions.add(new Fraction(Month.OCT, 0.1  ));
							fractions.add(new Fraction(Month.NOV, 0.1  ));
							fractions.add(new Fraction(Month.DEC, 0.1  ));
							Profile profile1 = new Profile(account,"A", fractions,"http://profile.com/1/\"");
							profileRepository.save(profile1);

							Set<Fraction> fractionsNew = new HashSet();
							fractionsNew.add(new Fraction(Month.JAN, 0.2  ));
							fractionsNew.add(new Fraction(Month.FEB, 0.1  ));
							fractionsNew.add(new Fraction(Month.MAR, 0.1  ));
							fractionsNew.add(new Fraction(Month.APR, 0.1  ));
							fractionsNew.add(new Fraction(Month.MAY, 0.1  ));
							fractionsNew.add(new Fraction(Month.JUN, 0.0  ));
							fractionsNew.add(new Fraction(Month.JUL, 0.0  ));
							fractionsNew.add(new Fraction(Month.AUG, 0.1  ));
							fractionsNew.add(new Fraction(Month.SEP, 0.1  ));
							fractionsNew.add(new Fraction(Month.OCT, 0.1  ));
							fractionsNew.add(new Fraction(Month.NOV, 0.1  ));
							fractionsNew.add(new Fraction(Month.DEC, 0.0  ));
							Profile profile2 = new Profile(account,"B", fractionsNew,"http://profile.com/2/\"");
							profileRepository.save(profile2);
							Set<MeterReading> meterReadings = new HashSet();
							meterReadings.add(new MeterReading(Month.JAN,  1 ));
							meterReadings.add(new MeterReading(Month.FEB,  2 ));
							meterReadings.add(new MeterReading(Month.MAR,  3 ));
							meterReadings.add(new MeterReading(Month.APR,  4 ));
							meterReadings.add(new MeterReading(Month.MAY,  5 ));
							meterReadings.add(new MeterReading(Month.JUN,  6 ));
							meterReadings.add(new MeterReading(Month.JUL,  7 ));
							meterReadings.add(new MeterReading(Month.AUG,  8 ));
							meterReadings.add(new MeterReading(Month.SEP,  9 ));
							meterReadings.add(new MeterReading(Month.OCT,  10 ));
							meterReadings.add(new MeterReading(Month.NOV,  11 ));
							meterReadings.add(new MeterReading(Month.DEC,  12 ));
							Set<MeterReading> meterReadingsNew = new HashSet();
							meterReadingsNew.add(new MeterReading(Month.JAN,  1 ));
							meterReadingsNew.add(new MeterReading(Month.FEB,  2 ));
							meterReadingsNew.add(new MeterReading(Month.MAR,  3 ));
							meterReadingsNew.add(new MeterReading(Month.APR,  4 ));
							meterReadingsNew.add(new MeterReading(Month.MAY,  5 ));
							meterReadingsNew.add(new MeterReading(Month.JUN,  6 ));
							meterReadingsNew.add(new MeterReading(Month.JUL,  7 ));
							meterReadingsNew.add(new MeterReading(Month.AUG,  8 ));
							meterReadingsNew.add(new MeterReading(Month.SEP,  9 ));
							meterReadingsNew.add(new MeterReading(Month.OCT,  10 ));
							meterReadingsNew.add(new MeterReading(Month.NOV,  11 ));
							meterReadingsNew.add(new MeterReading(Month.DEC,  12 ));


							connectionRepository.save( new Connection(//account,
									Math.abs(new Random().nextLong()),
									profile1, meterReadings, "http://connection.com/1/"));
							connectionRepository.save( new Connection(//account,
									Math.abs(new Random().nextLong()),
									profile2, meterReadingsNew, "http://connection.com/1/"));
						});
	}

}