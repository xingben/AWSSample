/**
 * 
 */
package data;

/**
 * @author benxing
 *
 */
public class Location {
	private String datacenter;
	private String superpod;
	private String pod;
	private String device;
	
	/**
	 * @return the datacenter
	 */
	public String getDatacenter() {
		return datacenter;
	}
	
	/**
	 * @param datacenter the datacenter to set
	 */
	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}
	
	/**
	 * @return the superpod
	 */
	public String getSuperpod() {
		return superpod;
	}
	
	/**
	 * @param superpod the superpod to set
	 */
	public void setSuperpod(String superpod) {
		this.superpod = superpod;
	}
	
	/**
	 * @return the pod
	 */
	public String getPod() {
		return pod;
	}
	
	/**
	 * @param pod the pod to set
	 */
	public void setPod(String pod) {
		this.pod = pod;
	}
	
	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}
	
	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}
}
