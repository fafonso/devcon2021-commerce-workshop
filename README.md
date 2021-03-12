# Liferay Devcon 2021 
### Commerce Workshop: From 0 to Deploy in 60 minutes

Liferay 7.3 CE workspace, with supporting material and modules for the Liferay Commerce workshop, created for Liferay Devcon 2021.

#### Local environment setup

* Make sure you have docker installed and configured with enough memory to run Liferay
* Donwload this repo and from the root folder, run "./resetLocalDockerEnv.sh"
* If you get an error when starting Liferay after running "./resetLocalDockerEnv.sh", it's probably a race condition with the DB docker box. Please stop your docker-compose environment (cntrl + C), and run "docker-compose up".

#### Repository index

* Master branch has the final setup, with all the modules and additional data
* There is a branch for every section covered in the workshop
	* [Liferay Docker Baseline](https://github.com/fafonso/devcon2021-commerce-workshop/tree/s0-liferay-docker-baseline)
	* [Batch Engine](https://github.com/fafonso/devcon2021-commerce-workshop/tree/s1-batch-engine)
	* [Product List Entry Renderer](https://github.com/fafonso/devcon2021-commerce-workshop/tree/s2-1-add-custom-product-list-entry-renderer)
	* [Product Renderer](https://github.com/fafonso/devcon2021-commerce-workshop/tree/s2-2-add-custom-product-renderer)
	* [Shipping Engine](https://github.com/fafonso/devcon2021-commerce-workshop/tree/s3-1-add-custom-shipping-engine)
