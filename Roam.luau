local RoamTable = {}

function RoamTable.RoamRandomly(entityRootPart,entityHumanoid)
	if entityRootPart == nil then
		return
	end
	if entityRootPart.Parent then

		local counterForMoving = entityRootPart.Parent:GetAttribute("counterForMoving")
		counterForMoving += 1
		local entityPostion = entityRootPart.Position
		if counterForMoving >= 75 then
			counterForMoving = 0
			local randomPosition = Vector3.new(math.random(entityPostion.X-7,entityPostion.X+7), 0, math.random(entityPostion.Z-7,entityPostion.Z+7))
			entityHumanoid:MoveTo(randomPosition)
		end

		entityRootPart.Parent:SetAttribute("counterForMoving", counterForMoving)
	end
end



return RoamTable
