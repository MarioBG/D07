package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WelcomeMessage;
import repositories.WelcomeMessageRepository;

@Component
@Transactional
public class StringToWelcomeMessageConverter implements Converter<String, WelcomeMessage> {

	@Autowired
	WelcomeMessageRepository welcomemessageRepository;

	@Override
	public WelcomeMessage convert(String text) {
		WelcomeMessage result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = welcomemessageRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
