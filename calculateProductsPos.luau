local DynamicCalculationBombing = {}

function DynamicCalculationBombing.CalculateAmountOfFruits(TypeOfShelf,ShelfBeingFilled,product)
	local XFruit = product.Size.X
	local ZFruit = product.Size.Z
	local XShelf, ZShelf, TopLeftCorner
	local positions = {}
	local pathToTheHold = nil
	
	if TypeOfShelf == "FruitHolder" then
		XShelf = ShelfBeingFilled.HoldPart.Size.X
		ZShelf = ShelfBeingFilled.HoldPart.Size.Z
		TopLeftCorner = Vector3.new(-XShelf / 2, 0, -ZShelf / 2)
		pathToTheHold = ShelfBeingFilled.HoldPart.CFrame
	elseif TypeOfShelf == "NormalShelf" then
		XShelf = ShelfBeingFilled.Shelf1.Size.X
		ZShelf = ShelfBeingFilled.Shelf1.Size.Z
		TopLeftCorner = Vector3.new(-XShelf / 2, 0, -ZShelf / 2)
		pathToTheHold = ShelfBeingFilled.Shelf1.CFrame
	elseif TypeOfShelf == "Fridge" then
		XShelf = ShelfBeingFilled.Shelf1.Size.X
		ZShelf = ShelfBeingFilled.Shelf1.Size.Z
		TopLeftCorner = Vector3.new(-XShelf / 2, 0, -ZShelf / 2)
		pathToTheHold = ShelfBeingFilled.Shelf1.CFrame
	end

	local row = math.ceil(XShelf/XFruit)
	local column = math.ceil(ZShelf/ZFruit)
	
	--local total = row * column
row+=ShelfBeingFilled:GetAttribute("RowRemove")
column+=ShelfBeingFilled:GetAttribute("ColumnRemove")
	for i = 0, row - 1 do
		for j = 0, column - 1 do
			local XOffset = i * XFruit + XFruit / 2 
			local ZOffset = j * ZFruit + ZFruit / 2

			local localPosition = TopLeftCorner + Vector3.new(XOffset, 0, ZOffset)
			local worldPosition = pathToTheHold:PointToWorldSpace(localPosition)
			table.insert(positions, worldPosition)
end
	end
	return positions

end

return DynamicCalculationBombing

