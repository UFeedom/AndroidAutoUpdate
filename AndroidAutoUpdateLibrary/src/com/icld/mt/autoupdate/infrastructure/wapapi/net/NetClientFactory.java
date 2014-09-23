
package com.icld.mt.autoupdate.infrastructure.wapapi.net;



public class NetClientFactory {
	
	public static NetClient getClient(){
		return new HttpNetClient();
	}
	

}

