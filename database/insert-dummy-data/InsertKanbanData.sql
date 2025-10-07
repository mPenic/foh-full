
INSERT INTO kanban.Tasks
  (Title,Description,Priority,StatusId,CreatedBy,AssignedTo,CreatedAt,UpdatedAt,CompanyId,RoleIds)
VALUES
 ('Restock bar supplies',
   'Order new bottles of whiskey & vodka, update inventory sheets.',
   3,
   1,             -- Open
   8,
   NULL,          -- unassigned
   '2025-04-18T11:00:00Z',
   '2025-04-18T11:00:00Z',
   1,
   ARRAY[2,3]     -- RoleId 2 = Bartender, 3 = Promotor
  ),
  -- promotional task, open to promotors
  ('Design event flyer',
   'Create and approve promotional flyer for May 1st event.',
   2,
   1,             -- Open
   8,
   NULL,
   '2025-04-18T12:00:00Z',
   '2025-04-18T12:00:00Z',
   1,
   ARRAY[3,4]     -- RoleId 3 = Promotor, 4 = Promotor+
  ),
  -- code‑review task, in progress by user #9
  ('Review reservation flow',
   'Verify that reservation statuses transition correctly from Pending → Confirmed.',
   2,
   3,             -- In Progress
   8,             -- CreatedBy: admin
   9,             -- AssignedTo: userId 9 (another engineer)
   '2025-04-18T13:00:00Z',
   '2025-04-18T13:15:00Z',
   1,
   ARRAY[]::integer[]  -- no specific role required
  );