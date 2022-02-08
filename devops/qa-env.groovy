def env() {
    return [
            slackGroup      : '',
            env              : 'qa',
            clusterReleaseEnv: 'qa',
            clusterDeployEnv : 'qa'
    ]
}
return this