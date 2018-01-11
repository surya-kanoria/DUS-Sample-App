#!/bin/sh
dus-deployer --config DeploymentConfig.json --platform android --react16 true --updateGraphVersion 0.0.0.0 --outputPath output --prodUpdateGraph output/UpdatePatch.json
node deployUpdatePatch.js
echo "Deployment done!!!"