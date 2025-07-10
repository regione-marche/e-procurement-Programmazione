package it.appaltiecontratti.tabellati.entity;

public class CFPIVAInfoInsert {

	private boolean checkCfValid;

	private boolean blockDuplicateCf;

	private boolean checkPIVAValid;

	private boolean blockDuplicatePIVA;

	public boolean isCheckCfValid() {
		return checkCfValid;
	}

	public void setCheckCfValid(boolean checkCfValid) {
		this.checkCfValid = checkCfValid;
	}

	public boolean isBlockDuplicateCf() {
		return blockDuplicateCf;
	}

	public void setBlockDuplicateCf(boolean blockDuplicateCf) {
		this.blockDuplicateCf = blockDuplicateCf;
	}

	public boolean isCheckPIVAValid() {
		return checkPIVAValid;
	}

	public void setCheckPIVAValid(boolean checkPIVAValid) {
		this.checkPIVAValid = checkPIVAValid;
	}

	public boolean isBlockDuplicatePIVA() {
		return blockDuplicatePIVA;
	}

	public void setBlockDuplicatePIVA(boolean blockDuplicatePIVA) {
		this.blockDuplicatePIVA = blockDuplicatePIVA;
	}

}
