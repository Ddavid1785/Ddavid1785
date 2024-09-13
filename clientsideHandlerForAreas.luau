wait(2)
local RepStorage = game:GetService("ReplicatedStorage")
local BoughtAreaFunction = RepStorage.BoughtAreaFunction
local player = game:GetService("Players").LocalPlayer
local CollectionService = game:GetService("CollectionService")
--local SG = game:GetService("StarterGui")

local buttons = CollectionService:GetTagged("AreaBuyButton")
local leaderstats = player:FindFirstChild("leaderstats")
local coins = leaderstats:FindFirstChild("Coins")
local confirmGui = player.PlayerGui.ConfirmGui
local bool

for i,v in buttons do
	v.Activated:Connect(function()
		if coins.Value > v:GetAttribute("Cost") then
			confirmGui.Enabled = true
			if not confirmGui:HasTag("PlayerClicked") then
				while not confirmGui:HasTag("PlayerClicked") do
					task.wait(0.1)
				end
			end
			if confirmGui:GetAttribute("Bought") == true then
				confirmGui:SetAttribute("Bought",false)
				confirmGui:RemoveTag("PlayerClicked")
				bool = BoughtAreaFunction:InvokeServer(v)
				if bool == true then
					bool = false
					v.Parent.Parent.CanCollide = false
					v.Parent.Parent.Transparency = 1
					v.Parent.Enabled = false
				end
			else
				confirmGui:RemoveTag("PlayerClicked")
			end

		end
		
	end)
end



