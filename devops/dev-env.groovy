def env() {
    return [
            slackGroup       : '',
            env              : 'dev',
            clusterReleaseEnv: 'dev',
            clusterDeployEnv : 'dev'
    ]
}
return this