package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PersonalRecord;

@Component
@Transactional
public class PersonalRecordToStringConverter implements Converter<PersonalRecord, String> {

	@Override
	public String convert(PersonalRecord personalrecord) {
		String result;

		if (personalrecord == null) {
			result = null;
		} else {
			result = String.valueOf(personalrecord.getId());
		}
		return result;
	}
}
