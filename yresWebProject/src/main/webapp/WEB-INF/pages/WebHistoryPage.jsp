<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Yres - web history</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/WebHistoryPageCss.css<%=cssVersion%>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Roboto:regular,italic&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Inter:regular,italic&display=swap"
	rel="stylesheet">
</head>
<body id="ibrvou">
	<div id="ijp2zh">
		<form method="get" action="Home" id="iglplu">
			<button type="submit" id="ib78ph">Home</button>
		</form>
		<c:if test="${sessionLogin.loggedIn == false}">
			<div id="ilm4tj">
				<form method="post" action="sLogin" id="ic5esq">
					<div id="igvknh">
						<label id="isaekj">Username<br /></label><input type="text"
							placeholder="Enter your username" name="username"
							value="${sessionLogin.usernamePreTyped}" id="ih9voi" /><label
							id="inx1qz"><br>${sessionLogin.usernameErr}<br /></label>
					</div>
					<div id="ic93bf">
						<label id="iuit2k">Password<br /></label><input type="password"
							placeholder="Enter your password" name="password" id="i8h15f" /><label
							id="imi9zx"><br>${sessionLogin.passwordErr}<br /></label>
					</div>
					<button type="submit" id="ivnv8v">Log in</button>
				</form>
				<form method="get" action="createAccount" id="ix885q">
					<button type="submit" id="ixoedw">Create an account</button>
				</form>
			</div>
		</c:if>
		<c:if test="${sessionLogin.loggedIn == true}">
			<div id="ilcg8t">
				<form method="post" action="sLogout" id="icsvqi">
					<label id="idihl7">Welcome <span id="iziw2p">${sessionLogin.username}</span>,<br /></label><label
						id="i1esqz">You're logged in<br /></label>
					<button type="submit" id="ika14b">Log out</button>
				</form>
			</div>
		</c:if>
		<form method="get" action="feedback" id="iltvjz">
			<button type="submit" id="idlvn2">Give feedback / bug report</button>
		</form>
	</div>
	<div id="i2uo19">
		<div id="if1d0a">
			About '<span id="i4gu5g">y-res.com</span>'
		</div>
		<div id="iwtec6">
			<span id="itgqbk">y-res.com</span> was created to satisfy my own
			curiosity about different topics in Java. I use this website as a
			playground for trying new approaches as I continue to learn new
			things which indirectly reflects my learning path when looking at its
			history. The best way to look at a project's history is to explore
			that project's Git repository. Here in this page, I simplified the
			timeline of <span id="i0r79j">y-res.com</span> into a digestible
			format. You might find this informative if you want to know how much
			I know about Java.<br /> <br />*The timeline below might not
			contain all versions, it shows only the important versions for
			simplicity. If you'd like to see every version available, please
			visit my Github.<br />
		</div>
		<div id="ibyi03">
			<div id="ikras6">
				<br />  <span id="ixx9b7h">●</span><br />   |<br />   |<br />   |<br />   |<br />   |<br />  <span
					id="ibel00f">●</span><br />   |<br />   |<br />   |<br />   |<br />   |<br />   |<br />  <span
					id="izjpsiw">●</span><br />   |<br />   |<br />   |<br />   |<br /> 
				 |<br id="ii2qbdf" draggable="true" />  <span id="izl4oqg">●</span><br
					id="io1lgak" draggable="true" />   |<br id="igb2meu"
					draggable="true" />   |<br id="izj7xjj" draggable="true" />   |<br
					id="iufk0ia" draggable="true" />   |<br id="ii1jufj"
					draggable="true" />   |<br id="iywq3kk" draggable="true" />   |<br
					id="i56jrnv" draggable="true" />   |<br id="i1mavnl"
					draggable="true" />   |<br id="iviku5k" draggable="true" />  <span
					id="iwflxhd">●</span><br id="iyjcmmv" draggable="true" />   |<br
					id="ip8s30r" draggable="true" />   |<br id="i996nsn"
					draggable="true" />   |<br id="ixqeldf" draggable="true" />   |<br
					id="i7qzufa" draggable="true" />   |<br id="ixgzlsg"
					draggable="true" />   |<br id="iybeji8" draggable="true" />  <span
					id="i5nunyt">●</span><br id="i0obzfj" draggable="true" />   |<br
					id="inedgwg" draggable="true" />   |<br id="ikp6nt1"
					draggable="true" />   |<br id="ify8o3d" draggable="true" />   |<br
					id="is8yyv6" draggable="true" />   |<br id="izejffe"
					draggable="true" />   |<br id="iipz4se" draggable="true" />   |<br
					id="ix3bds9" draggable="true" />   |<br id="iowjwtu"
					draggable="true" />   |<br id="igjtzfh" draggable="true" />   |<br
					id="ivgejji" draggable="true" />  <span id="i5rkubb">●</span><br
					id="i2wd0ve" draggable="true" />   |<br id="ivzbdd2"
					draggable="true" />   |<br id="i3co3o9" draggable="true" />   |<br
					id="i4jly92" draggable="true" />   |<br id="im3kpo5"
					draggable="true" />   |<br id="ic5p7x4" draggable="true" />   |<br
					id="i3k2n8x" draggable="true" />   |<br id="iub6mih"
					draggable="true" />   |<br id="ibp2iy3" draggable="true" />  <span
					id="in2zcek">●</span><br id="ip5vrms" draggable="true" />   |<br
					id="ivwitzi" draggable="true" />   |<br />   |<br />   |<br
					id="iqp15ga" draggable="true" />   |<br id="iabhh57"
					draggable="true" />   |<br id="izdzb1l" draggable="true" />   |<br
					id="iw19tg1" draggable="true" />   |<br id="i18kbaf"
					draggable="true" />   |<br id="iqr547i" draggable="true" />   |<br
					id="iswfclt" draggable="true" />   |<br id="iyk2uuc"
					draggable="true" />  <span id="i7ftiqq">●</span><br id="ip6opko"
					draggable="true" />   |<br id="izd8ydq" draggable="true" />   |<br
					id="igo45a7" draggable="true" />   |<br id="iweavp3"
					draggable="true" />   |<br id="izuh7hv" draggable="true" />   |<br
					id="ii1bpnb" draggable="true" />   |<br id="ia0r7hf"
					draggable="true" />   |<br id="iq49y1l" draggable="true" />  <span
					id="i0ru7ax">●</span><br id="ieuo5oc" draggable="true" />   |<br
					id="ids379m" draggable="true" />   |<br id="i1qoeo7"
					draggable="true" />   |<br id="i75ssnl" draggable="true" />   |<br
					id="ii3jqxk" draggable="true" />   |<br id="ihyzgm7"
					draggable="true" />   |<br id="i8onqqf" draggable="true" />   |<br
					id="ie17xz7" draggable="true" />   |<br id="ia5se7i"
					draggable="true" />  <span id="ioxutbt">●</span><br id="iexoj3i"
					draggable="true" />   |<br id="i5jwfja" draggable="true" />   |<br
					id="ikj3kr7" draggable="true" />   |<br id="i973gwo"
					draggable="true" />   |<br id="ih8wc1k" draggable="true" />   |<br
					id="ix7n18r" draggable="true" />   |<br id="ipksq11"
					draggable="true" />   |<br id="izq0uag" draggable="true" />   |<br
					id="iew6ysu" draggable="true" />  <span id="im1oywy">●</span><br
					id="ivpkhjh" draggable="true" />   |<br id="icz20f5"
					draggable="true" />   |<br id="iekyudi" draggable="true" />   |<br
					id="iul1wvz" draggable="true" />   |<br />   |<br />   |<br /> 
				 |<br id="imwvthf" draggable="true" />   |<br />  <span
					id="ib5zv7f">●</span><br />   |<br id="iuzslkj" draggable="true" /> 
				 |<br />   |<br id="ipvtb4h" draggable="true" />   |<br
					id="ivfkqm1" draggable="true" />   |<br id="iquwx5h"
					draggable="true" />   |<br id="i831g69" draggable="true" />   |<br
					id="iasc09f" draggable="true" />  <span id="iczwo9y">V</span><br />
			</div>
			<div id="it5trm">
				<span id="i6zvca">Version 0.1</span>     ==>     commit hash:
				dc763a8 (tag: v0.1) <span id="i2lcop">Getting started with
					Java servlets</span><br /> <span id="iwqfel">13 Jan 2025</span><br />   
				                              - The first version in Git. This is
				servlets-based, and it was deployed on AWS Elastic Beanstalk. <br /> 
				                                   Nothing fancy, just me starting
				to learn about Git, Java servlets, and deployment on AWS.<br />   
				      <br /> <span id="i95gc0e"><span id="i07y03l"> 
						        </span>●</span><br /> <span id="io4241">Version 0.3</span>     ==>   
				 commit hash: 8b3e352 (tag: v0.3) <span id="ij5b2m">Getting
					familiar</span><br /> <span id="i270xh">16 Jan 2025</span><br />       
				                          - Fixing AWS issue where environment
				variables were inaccessible only on AWS server.<br />             
				                    - Adding a class 'SessionManager' to manage data
				in the session so that we don't have to modify <br />             
				                       the session directly, making it less
				error-prone.<br />          <br /> <span id="ih9ugkg"><span
					id="iqq5xsg">          </span>●</span><br /> <span id="i93lbt">Version
					0.4</span>     ==>     commit hash: 68ebd19 (tag: v0.4) <span id="iwrpgg">Dealing
					with AWS issue</span><br /> <span id="i1j5cf">19 Jan 2025</span><br /> 
				                                - Large environment variable values
				have been moved to the database as AWS limits the size of <br />   
				                                 environment variables.<br />     
				    <br /> <span id="i8vw8d3"><span id="i60d2sx">   
						      </span>●</span><br /> <span id="i824rz">Version 0.5</span>     ==>   
				 commit hash: 22e0375 (tag: v0.5) <b>UTC+7 is the standard</b><br />
				<span id="io9vpf">19 Jan 2025</span><br />                         
				         - Many features relied on time which caused inconsistency
				when testing locally and on AWS server. <br />                     
				                To solve this issue, server time zone detection was
				added. Any time zone is converted to UTC+7 <br />                 
				                    before further operations for consistency
				purpose.<br />                                   - Adding website
				usage tracking. It's a simple feature counting number of times a
				page has been <br />                                      visited
				by a client.<br />          <br /> <span id="iw9fc8g"><span
					id="id4pu23">          </span>●</span><br /> <span id="itkeu2">Version
					0.71</span>    ==>     commit hash: df3880e (tag: v0.71) <span id="ia38yf">Noticing
					something weird</span><br /> <span id="ik22ko">24 Jan 2025</span><br /> 
				                                 - I saw some abnormal behavior from
				the data obtained from usage tracking. I presumed those <br />     
				                                could be something inhuman. In this
				version, I added an operation to collect IP addresses of the <br /> 
				                                    incoming requests for further
				investigation.<br />          <br id="i07uuh" draggable="true" />
				<span id="ibuzfrw"><span id="ipoae6m">          </span>●</span><br />
				<span id="i4t9di">Version 0.8</span>     ==>     commit hash:
				179bb6b (tag: v0.8) <span id="ifjj84">Filtering BOTs</span><br
					id="i1d53k" draggable="true" /> <span id="ixc7c9">25 Jan
					2025</span><br id="iozr0h" draggable="true" />                           
				       - At this point, I was quite certain that those requests are
				spam bot requests. So, I added a <br />                           
				          preliminary process to filter out those inhuman requests
				before it reaches the logical part of the <br />                   
				                  application. According to the information
				collected, most requests are from outside of Thailand, <br />     
				                                and since the visitors of this
				website are expected to be from Thailand, I redirected non-Thailand
				<br />                                      requests to the error
				page. For 0.001% possibility where a real human might access this
				website <br />                                      from other
				countries, I still allowed a level of compromise by letting those
				minority of real human <br />                                     
				to send a feedback ticket, so that I can manually white-list them
				case by case.<br />          <br id="ibikjd" draggable="true" /> <span
					id="iloktq6"><span id="ipqjzod">          </span>●</span><br /> <span
					id="imsltp">Version 1.0</span>      ==>     commit hash: 03486d9
				(tag: v1.0) <span id="i0rhkf">Converting to Spring</span><br
					id="i7ld4a" draggable="true" /> <span id="iwrdmx">19 Feb
					2025</span><br id="iqdup8" draggable="true" />                           
				       - Turning this project from traditional servlets into Spring.
				At this point, I was learning Spring and <br />                   
				                  Spring MVC. Starting from the most fundamental
				part like servlets helped me understand Spring <br id="ilet3f"
					draggable="true" />                                      from its
				core which was very beneficial in the long term. Spring is like a
				two-sided coin, it has a <br id="iarrdt" draggable="true" />       
				                              level of abstraction which on one side
				brings a huge quality-of-life upgrade, while on the other <br
					id="ivbcnn" draggable="true" />                                   
				  side we will just forget it someday if we don't really understand
				how it works under the hood.<br id="ijex1n" draggable="true" />   
				      <br /> <span id="iz05e2b"><span id="ix3lram"> 
						        </span>●</span><br /> <span id="im0522">Version 1.3</span>      ==>   
				 commit hash: 7624954 (tag: v1.3) <span id="i70x3h">Converting
					to Spring Boot</span><br /> <span id="iebrjn">17 Mar 2025</span><br /> 
				                                 - Turning this project from Spring
				to Spring Boot. I was trying to get used to Spring Boot, and the <br /> 
				                                    best way to do it is to actually
				do it. Spring Boot is like a new era of Spring. It upgrades quality
				of <br />                                      life even further
				from what normal Spring does.<br />                               
				   - As of this version, this project has had its own embedded
				Tomcat server by the power of Spring <br />                       
				              Boot. Unfortunately, JSP somehow just didn't work with
				JAR packaging, which is the default <br />                         
				            packaging for Spring Boot project with embedded Tomcat.
				The workaround is to package it into <br />                       
				              WAR instead of JAR. In other words, this project has
				an embedded Tomcat within a WAR file, so <br />                   
				                  no need for external Tomcat.<br />          <br />
				<span id="iwjzcic"><span id="irk2cyu">          </span>●</span><br />
				<span id="i7kmvh">Version 1.4</span>      ==>     commit hash:
				65693a2 (tag: v1.4) <span id="iyo2rk">Multi-threading</span><br />
				<span id="iepw2x">23 Mar 2025</span><br />                         
				         - I was interested in multi-threading, so I dove deep into
				this topic and found out how severe race <br />                   
				                  condition was. As Spring is based on
				multi-threading and I had been building this project without <br /> 
				                                    race conditions in mind since
				the beginning, I went back and overhauled the entire code in this <br /> 
				                                    project so that it had a strong
				structure against race conditions.<br />          <br /> <span
					id="i91oyp6"><span id="io4etng">          </span>●</span><br /> <span
					id="ixwtnh">Version 1.6</span>      ==>     commit hash: 12089d8
				(tag: v1.6) <span id="izvb6p">Spring REST</span><br /> <span
					id="iij8nl">21 Apr 2025</span><br />                             
				     - Adding REST API to this website. I'd been learning REST for a
				while and wanted to make use of it. <br />                         
				            So, I added a few REST API paths that might come in
				handy when I want to quickly see some <br />                       
				              information. So now, instead of going to MySQL
				workbench, I can just open a browser and take a<br />             
				                        sneak peek of the information in the
				database. And guess what!!! Even you can try it out!!!! Just <br /> 
				                                    go to Home page and click 'Try
				REST api' button!!!<br />          <br /> <span id="i26tnsj"><span
					id="ipdi4gy">          </span>●</span><br /> <span id="ir5e0x">Version
					1.61</span>     ==>     commit hash: 12e9562 (tag: v1.61) <span
					id="iyqx4c">My first Domain Name, 'y-res.com'</span><br /> <span
					id="iqhj96">23 Apr 2025</span><br />                             
				     - I've bought a new Domain Name!!! I'd been using the Elastic
				Beanstalk's default endpoint since <br />                         
				            the beginning. I just got to the point that I wanted to
				expand my own knowledge to cover security <br />                   
				                  connection (https / SSL certificate) which AWS
				didn't allow me to do so with the default Elastic <br />           
				                          Beanstalk URL. As of this version, this
				web application will support 'https'. This is a good start to <br /> 
				                                    Spring Security which I'm
				planning to learn soon.<br />          <br /> <span id="imy9961"><span
					id="i06qjd5">          </span>●</span><br /> <span id="isq6wn">Version
					1.62</span>    ==>     commit hash: 9e304a9 (tag: v1.62)
				<span id="ikjrnd">BOT filtering just got redesigned</span><br /> <span
					id="icygr4">29 Apr 2025</span><br />                               
				   - Optimizing BOT filter. Our web application has been overwhelmed
				by random spam requests. This <br />                               
				      chunk of spam requests has significantly grown to the point
				that the free-tier limit of a library <br />                       
				              FilterBot implementing was reached. I added various
				layers of BOT detection before utilizing the <br />                                     
				library. For example, implementing our own caching system and
				finding the IP in the cache first, <br />                           
				          then leveraging our own IP blacklist, and saving the
				library for last if nothing earlier matches.<br /> <br />          <span
					id="ivxw5gt">●</span><br /> <span id="iq5y3ea">Version 1.71</span> 
				   ==>     commit hash: d027dc6 (tag: v1.71)
				<span id="i6ditj5">AWS to THAI DATA CLOUD</span><br /> <span
					id="iibf0gc">07 May 2025</span><br />                             
				      - Moving from AWS to THAI DATA CLOUD. As this web application
				continues to grow, some of the
				<br />                                       new features made me go
				beyond AWS's free-tier limit. I had to find a new server which is
				more
				<br />                                       affordable than AWS.
				THAI DATA CLOUD is the one I go for as it is significantly cheaper
				and <br />                                       offering a fixed
				monthly cost. Even though AWS provides many services out of the box
				which is <br /> 
				                                     very convenient for me to learn
				the flow of data within the server side, the cost was just too high
				<br />                                       for a learning project
				like this web application.<br /> <br />
			</div>
		</div>
	</div>
</body>
</html>