This is a sample project that uses [DUS](https://github.com/Flipkart/DUS) by Flipkart.

To run this project, perform the following steps

1. Install DUS on your machine: ```npm install -g dus-deployer@1.49 ```
2. Start the mock server: ```npm run server ```
3. Deploy the first bundle using DeploymentConfig which deployes
the master branch of this repository: ```sh deploy1.sh ```
4. Launch the app from android studio (Open the android folder in this repository)
5. Deploy a new update(update branch of this repository): ```sh deploy2.sh ```
6. Relaunch the app to see the changes.

