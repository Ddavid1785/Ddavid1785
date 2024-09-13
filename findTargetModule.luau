local FindTargetTable = {}

function FindTargetTable.findTarget(entityRootPart)
		local aggroDistance

	if entityRootPart.Parent then
		aggroDistance = entityRootPart.Parent:GetAttribute("AggroDistance")

		else
			return nil 
		end

		local target = nil
		
		
		--fixat ovo poslje da je za folder ne za cijeli replicated storage
		for i,v in game.Workspace:GetChildren() do
			local humanoid = v:FindFirstChild("Humanoid")
			local rootPart = v:FindFirstChild("HumanoidRootPart")
			
		if humanoid and rootPart and entityRootPart.Parent.Name ~= rootPart.Parent.Name  then
			
				if (entityRootPart.Position - rootPart.Position).magnitude < aggroDistance then
				
				aggroDistance = (entityRootPart.Position - rootPart.Position).magnitude
				
					target = rootPart

				end
			end
		end
		return target
	end	

return FindTargetTable
