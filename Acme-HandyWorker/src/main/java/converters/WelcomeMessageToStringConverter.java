package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WelcomeMessage;

@Component
@Transactional
public class WelcomeMessageToStringConverter implements Converter<WelcomeMessage, String> {

	@Override
	public String convert(WelcomeMessage welcomemessage) {
		String result;

		if (welcomemessage == null) {
			result = null;
		} else {
			result = String.valueOf(welcomemessage.getId());
		}
		return result;
	}
}
