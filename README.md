# FISH-9111 Reproducer

To reproduce FISH-9111 on Payara Micro
1. Open the project in IntelliJ.
2. Run with project using a Payara Micro run configuration (provided by the plugin).
3. Attempt to access the REST endpoint: `/api/hello/test`.  
    a. This may be performed via the browser or via CURL.  
4. Verify the Status 500: NoClassDefFound error message.

Building the associated PR (and changing this project's payara.version as necessary) should reveal the error has been fixed.





