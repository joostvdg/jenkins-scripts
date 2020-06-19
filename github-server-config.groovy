// <?xml version='1.1' encoding='UTF-8'?>
// <github-plugin-configuration plugin="github@1.29.5">
//   <configs>
//     <github-server-config>
//       <name>github1</name>
//       <apiUrl>https://api.github.com</apiUrl>
//       <manageHooks>false</manageHooks>
//       <credentialsId>github-token</credentialsId>
//       <clientCacheSize>20</clientCacheSize>
//     </github-server-config>
//   </configs>
//   <hookSecretConfig>
//     <credentialsId></credentialsId>
//   </hookSecretConfig>
// </github-plugin-configuration>

// For Endpoints, see this script: https://github.com/cloudbees/jenkins-scripts/blob/master/github-enterprise-api-endpoint.groovy
// For JCASC: https://github.com/jenkinsci/configuration-as-code-plugin/tree/master/demos/github
// Code for ServerConfig: https://github.com/jenkinsci/github-plugin/blob/master/src/main/java/org/jenkinsci/plugins/github/config/GitHubServerConfig.java

import jenkins.model.*;
import org.jenkinsci.plugins.github.config.*;
import java.util.*;


println '======================================'
println '== Configuring GitHub Server - start'
githubName          = 'github'
githubAPI           = 'https://api.github.com'
githubCredentials   = 'github-token'
GitHubPluginConfig gitHubPluginConfig = GlobalConfiguration.all().get(GitHubPluginConfig.class)
def configs = gitHubPluginConfig.getConfigs()

def configExist = false
if (configs == null || configs.isEmpty()) {
    println '= No configs found, adding one'
}  else { 
    for (GitHubServerConfig serverConfig : configs) {
        if (githubName.equals(serverConfig.getName())) {
            configExist = true
        }
    }
}

if (configExist) {
    println "= GitHub Server Config ${githubName} already exists leaving it intact"
} else {
    println "= GitHub Server Config ${githubName} not found, creating now!"
    def config = new GitHubServerConfig(githubCredentials) // credentialsId is the only param
    config.setName(githubName)
    config.setApiUrl(githubAPI)
    gitHubPluginConfig.getConfigs().add(config)
    gitHubPluginConfig.save()
}

println '== Configuring GitHub Server - end'
println '======================================'
