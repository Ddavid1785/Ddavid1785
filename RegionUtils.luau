local RegionUtilsTable = {
	 Areas = {}
}

function RegionUtilsTable.MakeRegion3(part)
	local regionMin = part.Position - (part.Size / 2)  
	local regionMax = part.Position + (part.Size / 2)
	local region = Region3.new(regionMin,regionMax)
	local regionCframe = region.CFrame
	local rotationCFrame = CFrame.Angles(0, math.rad(90), 0) 
	local regionCframe = region.CFrame * rotationCFrame

	return region
end
	
function RegionUtilsTable.RandomPosInRegion(region)
	local rotationCFrame = CFrame.Angles(0, math.rad(90), 0) 
	local regionCframe = region.CFrame * rotationCFrame
	local regionSize = region.Size
	local randomX = math.random(-regionSize.X / 2, regionSize.X / 2)
	local randomZ = math.random(-regionSize.Z / 2, regionSize.Z / 2)
	local randomPosition = regionCframe * Vector3.new(randomX, 15, randomZ)
	
	return randomPosition
end



return RegionUtilsTable
