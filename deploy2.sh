#!/bin/sh
dus-deployer --config DeploymentConfig-2.json --platform android --react16 true --updateGraphVersion 0.0.0.1 --outputPath output
node deployUpdatePatch.js