local ShoppingListTable = {}

function ShoppingListTable.CreateShoppingList(folderOfProductsAvaliable)
	local ShoppingList = {}
	--local numberOfProducts = math.random(2,5)
	local numberOfProducts = 5

	local availableProducts = folderOfProductsAvaliable:GetChildren()
	for i = 1, numberOfProducts do
		local randomProduct = availableProducts[math.random(1, #availableProducts)]
		if randomProduct:IsA("Folder") then
			local productsInFolder = randomProduct:GetChildren()
			randomProduct = productsInFolder[math.random(1, #productsInFolder)]
		end
		ShoppingList[randomProduct.Name] = math.random(1, 3)
	end	
	return ShoppingList
end

return ShoppingListTable
