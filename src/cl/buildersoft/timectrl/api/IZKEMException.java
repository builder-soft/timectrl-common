package cl.buildersoft.timectrl.api;

public class IZKEMException extends Exception {
	private static final long serialVersionUID = -2826402328957654194L;
	private String message = null;
	private Integer code = null;

	public IZKEMException(Integer code) {
		this.code = code;
		this.message = codeToString(code);
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	private static String codeToString(Integer code) {
		String out = null;
		switch (code) {
		case -100:
			out = "Operation failed or data not exist";
			break;
		case -10:
			out = "Transmitted data length is incorrect";
			break;
		case -5:
			out = "Data already exists";
			break;
		case -4:
			out = "Space is not enough";
			break;
		case -3:
			out = "Error size";
			break;
		case -2:
			out = "Error in file read/write";
			break;
		case -1:
			out = "SDK is not initialized and needs to be reconnected";
			break;
		case 0:
			out = "Data not found or data repeated";
			break;
		/**
		 * case 1: out = "Operation is correct"; break;
		 */
		case 4:
			out = "Parameter is incorrect";
			break;
		case 101:
			out = "Error in allocating buffer";
			break;
		default:
			out = "Unknow error";
			break;
		}

		return out;
	}

	public Integer getCode() {
		return code;
	}
}
