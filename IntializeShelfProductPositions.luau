wait(2)

local folderOfProductsAvaliable = game:GetService("ReplicatedStorage"):WaitForChild("ProductFolderArea1")
local CollectionService = game:GetService("CollectionService")

for i, v in folderOfProductsAvaliable:GetChildren() do
	if v:IsA("Folder") then
		for e, g in v:GetChildren() do
			local taggedShelves = CollectionService:GetTagged(g.Name)

			if #taggedShelves > 0 then
				local shelf = taggedShelves[1]

				local positionPart = shelf:FindFirstChild("PositionToTakeStuff")
				if positionPart then
					shelf:SetAttribute("PositionInFrontOfShelf", Vector3.new(positionPart.Position.X,positionPart.Position.Y,positionPart.Position.Z))
					positionPart:Destroy()
				else
					warn(shelf.Name .. " does not have PositionToTakeStuff part")
				end
			else
				warn("No shelves found with tag: " .. g.Name)
			end
		end
	end
end
