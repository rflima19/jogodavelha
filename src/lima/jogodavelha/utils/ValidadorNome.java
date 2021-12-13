package lima.jogodavelha.utils;

import java.util.regex.Pattern;

public class ValidadorNome implements ValidadorString {

	@Override
	public boolean test(String str) {
		if ((str.isBlank() == true) || (str.isEmpty() == true) || 
				(Pattern.matches("^.*[^A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s0-9_]+.*$", str) == true)) {
			return false;
		}
		return true;
	}
}
