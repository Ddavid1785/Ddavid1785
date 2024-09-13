local CalculateAmountFruits = require(game:GetService("ServerScriptService").ShelfManager.CalculateAmountOfProducts)
local collservice = game:GetService("CollectionService")

local fruitHolders = collservice:GetTagged("FruitHolder")
local fruitHolderTable = {}
local shelfTable = {}
local fridgeTable = {}

for e,g in fruitHolders do
	--gets a name of the fruit and finds it in a folder
	local clonedFruitName = g:GetAttribute("Fruit")
	local originalFruit = game:GetService("ReplicatedStorage").ProductFolderArea1["Fruit/Veggies"][clonedFruitName]
	
	--names the folder by fruit ex. "Apple Holder"
	g.Name = originalFruit.Name.."Holder"
	fruitHolderTable[g.Name] = {}
	
	--function to calculate positions for fruits
	local positions = CalculateAmountFruits.CalculateAmountOfFruits("FruitHolder", g, originalFruit)
	
	--a loop to put a fruit in each position
	for i, pos in positions do
		local clonedFruit = originalFruit:Clone()  
		clonedFruit.Name =  clonedFruitName
			clonedFruit.CFrame = CFrame.new(pos) * CFrame.Angles(math.rad(0), 0, math.rad(0))
		clonedFruit.CFrame = clonedFruit.CFrame * CFrame.new(0, clonedFruit.Size.Y / 2, 0)
		clonedFruit.Parent = g
		table.insert(fruitHolderTable[g.Name],clonedFruit)
	end
end



local Shelfs = collservice:GetTagged("NormalShelf")

for i,v in Shelfs do
	local clonedProductName = v:GetAttribute("Product")
	local originalProduct = game:GetService("ReplicatedStorage").ProductFolderArea1.NormalShelfProducts[clonedProductName]
	local positions = CalculateAmountFruits.CalculateAmountOfFruits("NormalShelf", v, originalProduct)
	local heightPlus = 0
	local rotationX = originalProduct:GetAttribute("RotationX") or 0
	local rotationY = originalProduct:GetAttribute("RotationY") or 0
	local rotationZ = originalProduct:GetAttribute("RotationZ") or 0
	v.Name = originalProduct.Name.."Shelf"
	shelfTable[v.Name] = {}

	for _,pos in positions do
		for e = 0,3,1 do
			local clonedProduct = originalProduct:Clone()  
			clonedProduct.Name =  clonedProductName
			clonedProduct.CFrame = CFrame.new(pos) * CFrame.Angles(math.rad(0), math.rad(90), math.rad(0))
			clonedProduct.CFrame = clonedProduct.CFrame * CFrame.new(0, clonedProduct.Size.Y / 2, 0)
				clonedProduct.CFrame = clonedProduct.CFrame * CFrame.new(0, heightPlus, 0)
			clonedProduct.CFrame = clonedProduct.CFrame * CFrame.Angles(math.rad(rotationX), math.rad(rotationY), math.rad(rotationZ))
			clonedProduct.Parent = v
			heightPlus+=1.554
			table.insert(shelfTable[v.Name],clonedProduct)
		end
		heightPlus = 0
	end
end

local Fridges = collservice:GetTagged("Fridge")

for i,v in Fridges do

	local clonedProductName = v:GetAttribute("Product")
	local originalProduct = game:GetService("ReplicatedStorage").ProductFolderArea1.FridgeItems[clonedProductName]
	local positions = CalculateAmountFruits.CalculateAmountOfFruits("Fridge", v, originalProduct)
	local heightPlus = 0
	local goBack = 0.3
	local rotationX = originalProduct:GetAttribute("RotationX") or 0
	local rotationY = originalProduct:GetAttribute("RotationY") or 0
	local rotationZ = originalProduct:GetAttribute("RotationZ") or 0
	v.Name = originalProduct.Name.."Fridge"
	fridgeTable[v.Name] = {}
	for _,pos in positions do
		for e = 0,2,1 do
			local clonedProduct = originalProduct:Clone()  
			clonedProduct.Name =  clonedProductName
			clonedProduct.CFrame = CFrame.new(pos) * CFrame.Angles(math.rad(0), math.rad(90), math.rad(0))
			clonedProduct.CFrame = clonedProduct.CFrame * CFrame.new(0, clonedProduct.Size.Y / 2, 0)
			clonedProduct.CFrame = clonedProduct.CFrame * CFrame.new(0, heightPlus, 0)
			clonedProduct.CFrame = clonedProduct.CFrame * CFrame.Angles(math.rad(rotationX), math.rad(rotationY), math.rad(rotationZ))
			if clonedProductName == "Hamburger"  then
				clonedProduct.CFrame = clonedProduct.CFrame * CFrame.new(goBack,0,0)
			elseif clonedProductName == "Bloxiade"  then
				clonedProduct.CFrame = clonedProduct.CFrame * CFrame.new(0,0,goBack)
			end
			
			clonedProduct.Parent = v
			heightPlus+=1.584
			table.insert(fridgeTable[v.Name],clonedProduct)

		end
		heightPlus = 0
	end
end
