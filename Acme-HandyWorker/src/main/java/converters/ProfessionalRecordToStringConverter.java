package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ProfessionalRecord;

@Component
@Transactional
public class ProfessionalRecordToStringConverter implements Converter<ProfessionalRecord, String> {

	@Override
	public String convert(ProfessionalRecord professionalrecord) {
		String result;

		if (professionalrecord == null) {
			result = null;
		} else {
			result = String.valueOf(professionalrecord.getId());
		}
		return result;
	}
}
