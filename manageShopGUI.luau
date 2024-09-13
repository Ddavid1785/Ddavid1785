local ReplicatedStorage = game:GetService("ReplicatedStorage")
local StarterGui = game:GetService("StarterGui")

local ToolConfig = require(ReplicatedStorage.ToolConfig)

local toolsInShopFolder = workspace:FindFirstChild("ToolsInShop")
local descriptionOfWeaponFrame = script.Parent
local buttonTable = {}
local tableOfCostAndOrder = {}

local function InsertButtons()
	local scrollFrameChildren = script.Parent.Parent.FrameForWeapons:FindFirstChild("ScrollingFrame"):GetChildren()

	for i,v in scrollFrameChildren do
		if v:IsA("ImageButton") then
			table.insert(buttonTable,v)    
		end
	end    
end

local function connectButtonFunctionality()
	local function Activated(value)
		local ToolConfigTable = ToolConfig.getConfig(string.gsub(value.Name," Button",""))
		descriptionOfWeaponFrame.ImageOfWeapon.Image = value.Image
		descriptionOfWeaponFrame["Attack Damage"].Text = ToolConfigTable.Damage
		descriptionOfWeaponFrame.CoolDown.Text = ToolConfigTable.cooldown
		descriptionOfWeaponFrame.WeaponName.Text = string.gsub(value.Name," Button","")
		local cost = ToolConfigTable.Cost

		--local stringForCost = ""
		--if cost<100 then
		--	stringForCost = cost
		--elseif cost > 99 then
		--	stringForCost =  cost.."  "
		--elseif cost >  1000 then
		--	cost/=1000
		--	stringForCost = cost.."K"
		--end
		descriptionOfWeaponFrame.CostLabelRight.Text = cost
		descriptionOfWeaponFrame.CostLabelRight.ImageLabel.Visible = true
		descriptionOfWeaponFrame.BuyWeaponButton.Text = "Buy"
		if game:GetService("Players").LocalPlayer:HasTag("DidntClickOnWeaponInShopYet") then
			descriptionOfWeaponFrame.Visible = true
			game:GetService("Players").LocalPlayer:RemoveTag("DidntClickOnWeaponInShopYet")
		end
		
	end
	local function ActivatedButBought(value)
		local ToolConfigTable = ToolConfig.getConfig(string.gsub(value.Name," Button",""))
		descriptionOfWeaponFrame.ImageOfWeapon.Image = value.Image
		descriptionOfWeaponFrame["Attack Damage"].Text = ToolConfigTable.Damage
		descriptionOfWeaponFrame.CoolDown.Text = ToolConfigTable.cooldown
		descriptionOfWeaponFrame.WeaponName.Text = string.gsub(value.Name," Button","")
		descriptionOfWeaponFrame.CostLabelRight.Text = "Bought!"
		descriptionOfWeaponFrame.BuyWeaponButton.Text = "Equip!"
		descriptionOfWeaponFrame.CostLabelRight.ImageLabel.Visible = false
		if game:GetService("Players").LocalPlayer:HasTag("DidntClickOnWeaponInShopYet") then
			descriptionOfWeaponFrame.Visible = true
			game:GetService("Players").LocalPlayer:RemoveTag("DidntClickOnWeaponInShopYet")
		end
	end
	
	
	for _, value in buttonTable do
		value.Activated:Connect(function()
			if value:HasTag("Bought") then
				ActivatedButBought(value)
				return
			end
			Activated(value)
		end)
	end
end

local function SetDescriptions()
	StarterGui.WeaponGui.Frame.DescriptionOfWeaponFrame:GetAttributeChangedSignal("ButtonsReady"):Connect(connectButtonFunctionality)
	StarterGui.WeaponGui.Frame.DescriptionOfWeaponFrame:SetAttribute("ButtonsReady",nil)
end
	
task.delay(0.5, function()
	InsertButtons()
	connectButtonFunctionality()
	SetDescriptions()
end)
 
