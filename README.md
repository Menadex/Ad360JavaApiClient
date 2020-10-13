# Ad360 Java Api Client


## Introduction

The Ad360 Java API Client is meant to allow you to use any of the Ad360 Platform features programmatically. 
This way you can work at a large scale and automate your campaigns setups, management, or data analysis.

``Ad360JavaApiClient`` is a Java library that provides an interface
between your Java application and Ad360's 
<a href="http://ad360.media/api-doc.html" target="_blank">Campaign Manager API</a>. This
readme document details what you need to know to start using the Java Client.

## Quick Start

If you need any help with Ad360, feel free to visit <a href="https://support.ad360.media/hc/en-us">Ad360's Help Center</a> to consult its articles or submit a ticket from there to get in touch with the Support Team. 

Below are the pre-requisites steps you need to take before you can use the Java Client.

### Pre-requisites

#### Have a user  account in Ad360 

To use the Ad360JavaApiClient, you need to have a **valid Ad360 user account**. Your username and password used to log in the Ad360 console will be required too when using the Java API Client. 

If you don't already have an account, <a href="https://www.ad360.media/#contact-us">contact the Ad360 Sales team</a> to request an access to Ad360.

**IMPORTANT**: Your Ad360 username and password are yours and you should keep them secret. If other members of your team require access, you'll need to create separate users in Ad360 for each person.

#### Obtain An Ad360 API Access Token

In addition to the username and password, you will need to provide an **Ad360 API Access Token** to use the Ad360 API Java Client.
An access token is an opaque string that identifies an organization. 

Contrary to the username and password, that are personal, the **Ad360 API Access Token** is shared with your entire organization. Each API request executed with your **Ad360 API Access Token** will count towards your organization's Usage Plan, which includes a certain amount of requests per day allowed.

**IMPORTANT**: The Ad360 API Access Token needs to be requested before it is provisioned. The Ad360 API Access Token will be determined based on your organization's Usage Plan, which includes a certain number of requests per day. 
To request an Ad360 API Access Token please <a href="https://support.ad360.media/hc/en-us/requests/new">submit a request</a> to the Ad360 Support Team with "Requesting API Access Token" in the title of your ticket. In the body of your request, please state what type of operations you wish to perform with the API. A Usage Plan will be determined with the Ad360 Support Team based on your organization's needs. 

If you wish your organization's Usage Plan to be reviewed at any time, please <a href="https://support.ad360.media/hc/en-us/requests/new">submit a request</a> to get in contact with the Ad360 Support Team. 


### Install Java API Client

To start using Ad360 Java API Client, you first need to create a Maven Java project. You will find below the Maven dependency you need to add to your pom.xml file to run the Ad360 Java API Client:

```text
		<dependency>
			<groupId>com.ad360.api</groupId>
			<artifactId>client</artifactId>
			<version>1.0.0-SNAPSHOT</version>
		</dependency>
```


### Run Sample code
Here is the minimal code needed to retrieve an existing Line Item in your Ad360 account. 

In order to run any code with the Ad360 API Java Client, you will need to update the 3 following variables with your credentials:
- username
- password
- token

In order to run this specific code, you will need to update too the IDs of a valid Line Item and its parent Campaign, Advertiser and Account.

```java
import com.ad360.api.ApiException;
import com.ad360.api.client.Ad360ApiClient;
import com.ad360.api.client.LineItemApi;
import com.ad360.api.client.model.LineItem;

public class SampleGetLineItemById {

	static Ad360ApiClient ad360ApiClient;
	
	static String username = "YOUR_USERNAME";
	static String password = "YOUR_PASSWORD";
	static String token = "YOUR_ORGANIZATION_TOKEN";
	
	static String accountId = "YOUR_ACCOUNT_ID";
	static String advertiserId = "YOUR_ADVERTISER_ID";
	static String campaignId = "YOUR_ADVERTISER_ID";
	static String lineItemId = "YOUR_LINE_ITEM_ID";

	public static void main(String[] args) throws ApiException {

		ad360ApiClient = new Ad360ApiClient(username, password,token);

		testGetLineItemById();

	}
	
	public static void testGetLineItemById() throws ApiException {
		LineItemApi lineItemApi = new LineItemApi(ad360ApiClient);
		LineItem lineItem = lineItemApi.getLineItemById(advertiserId, campaignId, lineItemId);
		System.out.println("lineItem: \n" + lineItem.toString());
	}
}

```

### More Sample Codes

You will find additional sample codes in the **test** package of this project. 


#### API Clients Sample Code

**IMPORTANT:** Note that manipulating Time Series requires the use of another Client (the Ad360TimeseriesApiClient) contrary to all other operations (which require the Ad360ApiClient).

The sample classe below shows you how to connect using both the regular Ad360ApiClient, and the Ad360TimeseriesApiClient.

```java
import com.ad360.api.ApiException;
import com.ad360.api.client.Ad360ApiClient;
import com.ad360.api.client.Ad360TimeseriesApiClient;

public class TestApiClients {

	static String username = "YOUR_USERNAME";
	static String password = "YOUR_PASSWORD";
	static String token = "YOUR_ORGANIZATION_TOKEN";

	static Ad360ApiClient ad360ApiClient;
	static Ad360TimeseriesApiClient ad360TimeseriesApiClient;

	public static void main(String[] args) throws ApiException {

		TestInitAd360ApiClient();

		TestInitAd360TimeseriesApiClient();
	}

	public static void TestInitAd360ApiClient() throws ApiException {
		ad360ApiClient = new Ad360ApiClient(username, password, token);
	}

	public static void TestInitAd360TimeseriesApiClient() throws ApiException {
		ad360TimeseriesApiClient = new Ad360TimeseriesApiClient(username, password, token);

	}
}
```

#### API Connect - Retrieving Objects Sample Code

Please refer to <a href="https://github.com/Menadex/Ad360JavaApiClient/blob/master/Ad360%20API%20Client/src/test/java/com/ad360/api/client/TestApiConnect.java">this sample test class</a> to see how to connect to the API.

This class includes multiple examples of how to retrieve Ad360 objects like Advertisers, Campaigns, Line Items, Creatives, etc.

#### Time Series Sample Code

Please refer to <a href="https://github.com/Menadex/Ad360JavaApiClient/blob/master/Ad360%20API%20Client/src/test/java/com/ad360/api/client/TestApiTimeseries.java">this sample test class</a> to see how to manipulate Time Series.


## Ad360 API documentation

To get more information about the Ad360 API and see the ful API references, please refer to the <a href="http://ad360.media/api-doc.html" target="_blank">Campaign Manager API</a>.

### Help Center API articles

Additional information and best practives regarding the use of the API can be found in the <a href="
https://support.ad360.media/hc/en-us/categories/360002548900-Technical-Documentation-API-References">Help Center section relating to the API</a>. 
