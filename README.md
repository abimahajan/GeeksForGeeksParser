GeeksForGeeksParser
===================

Downloads html files while parsing geeksforgeeks.org website for different categories and for different companies.

Steps for running this parser:
1. Download the project from the url: https://github.com/abimahajan/GeeksForGeeksParser/archive/master.zip
2. Extract the contents.
3. Configure the properties file under /src depending upon your requirement(s).
4. For downloading html(s) depending on category, run the file Category.java under package com\geeks\parser\category.
5. For downloading htms(s) depending on company name, run the file CompanySpecific.java under package com\geeks\parser\company.




Properties present in geeks.properties:
1. LINKS_LIST: URLs specifying which all categories are needed for downloading the html(s). Each category's URL is separated by '@'. This value can be changed according to person's requirement.
eg: URL for category of Arrays is 'http://www.geeksforgeeks.org/category/c-arrays/' and so on.

2. COMPANY_BASE_URL: URL specifying start of each company's interview questions link. This value can be changed only when the URL changes in the server side.

3. COMPANY_NAME: List of company names whose interview questions are provided by geeksforgeeks. Each company's name is separated by '@'. This value can be changed according to person's requirement and also considering that geeksforgeeks has content about the same.
eg: If you want to download only Amazon interview questions you will write Amazon as the value of COMPANY_NAME.

4. EXCEPTION_COMPANY_NAME: List of companies name whose URL for parsing is different from others. Each company's name is separated by '@'. This value can be changed according to person's requirement and also considering that geeksforgeeks has content about the same. 
eg: Amazon company has its URL as: http://www.geeksforgeeks.org/forums/topic-tag/amazon/
and Adobe as : http://www.geeksforgeeks.org/forums/topic-tag/adobe-2/
Here Adobe will be added to the EXCEPTION_COMPANY_NAME list.

5. IS_PROXY_NEEDED: States a boolean value true or false depending if proxy is needed or not. Proxy may be needed if you are using this project in behind some firewalls.

6. PROXY_HOST: Specifies the value of proxy host if you are using proxy.

7. PROXY_PORT: Specifies the value of proxy port if you are using proxy.

8. CATEGORY_DIV_FOR_PAGES: Specifies the value of div to parse the value of number of pages in each category as specified in LINKS_LIST. This value cannot be changed.

9. CATEGORY_SPAN_FOR_PAGES: Specifies the value of span to parse the value of number of pages in each category name as specified in LINKS_LIST. This value cannot be changed.

10. CATEGORY_DIV_FOR_LINKS: Specifies the value of div to parse the hyperlink for each of the questions as displayed in each category's page. This value cannot be changed.

11. COMPANY_CLASS_FOR_PAGES: Specifies the value of class to parse the value of number of pages in each company name as specified in COMPANY_NAME. This value cannot be changed.

12. COMPANY_CLASS_FOR_LINKS: Specifies the value of class to parse the hyperlink for each of the questions as displayed in each company's page. This value cannot be changed.

13. BASE_FOLDER: Location of your folder where html(s) are to be downloaded.
eg: C:\Documents\GeeksDocs

14. STOP_CHARACTER_FOLDER: Specifies the list of values which are not permissible during file creation. This value can be changed.

15. WAIT_TIME: Specifies the time (in miliseconds) to wait between two categorys or two companies. By default it is set to 1. This value can be changed.
