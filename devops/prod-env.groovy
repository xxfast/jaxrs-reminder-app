def env() {
    return [
            slackGroup      : '',
            env              : 'prod',
            clusterReleaseEnv: 'prod',
            clusterDeployEnv : 'prod'
    ]
}

return this
