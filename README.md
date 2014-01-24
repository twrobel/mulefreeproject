mulefreeproject
===============

Sample project in Mule ESB.
Written in Mule Studio 3.5.

The app :
- processes city census csv files fetched from http://www12.statcan.gc.ca/census-recensement/index-eng.cfm
- stores csv rows in mongo DB
- exposes simple REST service to fetch two of the census topics as JSON over HTTP
- displays html page with bar charts and census info
The app has several flows, see png image in the main directory.
