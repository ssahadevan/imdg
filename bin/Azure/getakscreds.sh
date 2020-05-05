echo "Usage: getakscreds ssdemo"
echo "     : where  ssdemo is the name of the aks cluster"
az aks get-credentials --resource-group ssHazelcastDemo --name $1
