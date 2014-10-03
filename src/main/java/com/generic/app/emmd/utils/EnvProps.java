package com.generic.app.emmd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class EnvProps {
    private static final String propsFileName = "envProps.properties";

    //private static final Log log = LogFactory.getLog(EnvProps.class);

    private Properties envProperties = null;

    private static EnvProps instance = null;

    private EnvProps() {
        loadProperties();
    }

    private void loadProperties() {
      //  log.debug("Loading "+propsFileName+" ..... ");

        InputStream in = null;

        envProperties = new Properties();
        in = this.getClass().getResourceAsStream("/"+propsFileName);

        try {
            if(in != null) {
                envProperties.load(in);
        //        log.debug(propsFileName+" loaded successfully");
            }
        }
        catch (IOException ioe) {
            System.out.println("Exception caught while loading "+propsFileName);
            ioe.printStackTrace();
        }
        finally {
            if(in != null ){
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static EnvProps getInstance() {
        if (instance == null) {
            synchronized(EnvProps.class) {
                if (instance == null) {
            //    	log.debug("Creating instance of EnvProps class");
                    instance = new EnvProps();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        String property = envProperties.getProperty(key);
        if(property == null) {
          //  log.debug("Property "+key+" not found in "+propsFileName);
        }
        return property;
    }
}